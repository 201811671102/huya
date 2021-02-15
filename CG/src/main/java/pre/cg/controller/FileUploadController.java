package pre.cg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pre.cg.base.ResultUtil;
import pre.cg.base.dto.ResultDTO;

import java.io.File;
import java.io.IOException;

/**
 * @auto CG
 * 文件上传
 * @date 2019/12/31 15:07
 */
@Controller
@RequestMapping("/file")
@Api(value = "FileUploadController|一个用来测试文件上传的控制器",tags = {"说明","说明"})
public class FileUploadController {


    @PostMapping("/fileUploadController")
    @ResponseBody
    @ApiOperation(
            value = "上传文件",     //value : 方法描述
            notes = "提交内容不能大于100m")   //notes : 提示内容)
    public ResultDTO fileUpload(
            @ApiParam(value = "上传的文件", required = true)
            @RequestParam(value = "file", required = true)MultipartFile filename){
        System.out.println(filename.getOriginalFilename());
        try {
            filename.transferTo(new File("E:/"+filename.getOriginalFilename()));
            return new ResultUtil().SUccess();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultUtil().Error("401","系统错误");
    }
}
