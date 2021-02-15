package pre.cg.ftp.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pre.cg.ftp.base.DTO.ResultDTO;
import pre.cg.ftp.base.ResultUtils;
import pre.cg.ftp.base.code.Code;
import pre.cg.ftp.ftp.FTPOperation;
import pre.cg.ftp.pojo.UserFile;
import pre.cg.ftp.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName FTPController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/19 0:03
 **/
@RequestMapping("/file")
@Controller
@Api(value = "FileController")
public class FTPController {

    @Autowired
    private FTPOperation ftpOperation;
    @Autowired
    private FileService fileService;

    // 业务线程池
    private static final ExecutorService bizThreadPool =
            new ThreadPoolExecutor(10, 512, 60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(20000));

    class MyCallable<T> implements Callable<T>{
        private Listener<T> listener;

        public MyCallable(Listener<T> listener) {
            this.listener = listener;
        }

        @Override
        public T call() throws Exception {
            return listener.result();
        }
    }
    interface Listener<T>{
        T result() throws Exception;
    }


    @RequestMapping(value = "/file",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传文件",notes = "400 上传失败")
    public ResultDTO<UserFile> upfile(
            @ApiParam(value = "类型",required = true)@RequestParam(value = "type",required = true,defaultValue = "photo") String type,
            @ApiParam(value = "照片",required = true)@RequestParam(value = "photo",required = true) MultipartFile photo){
        Future<ResultDTO<UserFile>> submit = bizThreadPool.submit(new Callable<ResultDTO<UserFile>>() {
            @Override
            public ResultDTO<UserFile> call() throws Exception {
                String phototype = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf("."));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String path = type+"/"+simpleDateFormat.format(System.currentTimeMillis());
                String name = System.currentTimeMillis()+phototype;
                boolean b = ftpOperation.uploadToFtp(photo.getInputStream(),name,path);
                if(!b){
                    return ResultUtils.error(Code.FAil.code,photo.getOriginalFilename()+"文件上传失败");
                }
                UserFile userFile = new UserFile();
                userFile.setFilepath("http://129.204.145.82:70/"+path+"/"+name);
                userFile.setFiletime(new Date());
                fileService.addFile(userFile);
                return ResultUtils.success(userFile);
            }
        });
        try {
            return submit.get();
        } catch (Exception e) {
            return ResultUtils.error(Code.ERROR.code,e.toString());
        }
    }

    @RequestMapping(value = "/file",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value ="删除文件",notes = "500 删除失败")
    public ResultDTO<String> defile(
            @ApiParam(value = "fid",required = true)@RequestParam(value = "fid",required = true)Integer fid
    ){
        Future<ResultDTO<String>> future = bizThreadPool.submit(new Callable<ResultDTO<String>>() {
            @Override
            public ResultDTO<String> call() throws Exception {
                UserFile userFile = null;
                if ((userFile = fileService.getFile(fid)) != null) {
                    String fileUrl = userFile.getFilepath();
                    Integer firstChar = fileUrl.indexOf("70") + 2;
                    Integer lastChar = fileUrl.lastIndexOf("/");
                    String fileName = fileUrl.substring(lastChar + 1);
                    String filePath = fileUrl.substring(firstChar, lastChar);
                    ftpOperation.delectFile(fileName, filePath);
                    fileService.removeFile(fid);
                    return ResultUtils.success();
                }
                return ResultUtils.error(Code.UNFOUNDED.code, "没有此文件");
            }
        });

        try {
           return future.get();
        }catch (Exception e){
            return ResultUtils.error(Code.ERROR.code,e.getMessage());
        }
    }

    @RequestMapping(value = "/file",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有文件")
    public ResultDTO<PageInfo> getAll(
            @ApiParam(value = "页码",required = true)@RequestParam(value = "offset",required = true,defaultValue = "1")Integer offset,
            @ApiParam(value = "每页条数",required = true)@RequestParam(value = "limit",required = true,defaultValue = "10")Integer limit
    ){
        Future<ResultDTO<PageInfo>> future = bizThreadPool.submit(new Callable<ResultDTO<PageInfo>>() {
            @Override
            public ResultDTO<PageInfo> call() throws Exception {
                PageHelper.startPage(offset, limit);
                List<UserFile> userFileList = fileService.getALL();
                PageInfo<UserFile> pageInfo = new PageInfo<UserFile>(userFileList);
                return ResultUtils.success(pageInfo);
            }
        });
        try {
           return future.get();
        }catch (Exception e){
            return ResultUtils.error(Code.ERROR.code,e.getMessage());
        }
    }

    @RequestMapping(value = "/fileone",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取一个文件")
    public ResultDTO<UserFile> getOne(
            @ApiParam(value = "fid",required = true)@RequestParam(value = "fid",required = true)Integer fid
    ){
        Future<ResultDTO<UserFile>> future = bizThreadPool.submit(new Callable<ResultDTO<UserFile>>() {
            @Override
            public ResultDTO<UserFile> call() throws Exception {
                return ResultUtils.success(fileService.getFile(fid));
            }
        });
        try {
            return future.get();
        }catch (Exception e){
            return ResultUtils.error(Code.ERROR.code,e.getMessage());
        }
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "下载一个文件")
    public void download(
            HttpServletResponse response, HttpServletRequest request,
            @ApiParam(value = "fid",required = true)@RequestParam(value = "fid",required = true)Integer fid
    ){
        bizThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                BufferedInputStream bufferedInputStream = null;
                try {
                    UserFile userFile = fileService.getFile(fid);
                    String fileUrl = userFile.getFilepath();
                    Integer firstChar = fileUrl.indexOf("70")+2;
                    Integer lastChar = fileUrl.lastIndexOf("/");
                    String fileName = fileUrl.substring(lastChar+1);
                    String filePath = fileUrl.substring(firstChar,lastChar);
                    inputStream = ftpOperation.downloadFile(fileName,filePath);
                    response.setContentType("image/jpeg");
                    response.setCharacterEncoding("utf-8");
                    response.addHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(fileName,"UTF-8"));
                    OutputStream outputStream = response.getOutputStream();
                    bufferedInputStream = new BufferedInputStream(inputStream);
                    byte[] bytes = new byte[1024];
                    int i = -1;
                    while ((i=bufferedInputStream.read(bytes)) != -1){
                        outputStream.write(i);
                    }
                }catch (Exception e){

                }finally {
                    if (bufferedInputStream!=null){
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream!=null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
