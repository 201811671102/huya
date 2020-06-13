package com.chuangshu.studentworker.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Jiaqi Guo
 * @date 2020/6/12  - 12:53
 */

public class FileUtil {
    //上传文件
    public static String upLoadFile(MultipartFile file,String partRealName) throws IOException {
        //获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的后缀
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成新的文件名称(这个文件名存到数据库中)
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss")+ UUID.randomUUID().toString().replace("-","")+suffixName;
        //处理根据日期生成的目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + partRealName;

        File dateDir = new File(realPath);
        //处理文件f1上传
        if (!dateDir.exists())dateDir.mkdirs();
        file.transferTo(new File(dateDir,newFileName));
        //返回文件的新名字
        return newFileName;
    }
    //下载文件
    public static List downloadFile(String fileName, String partRealName, HttpServletResponse httpServletResponse) throws IOException {
        //根据文件信息中文件名字 和 文件的存储路径获取文件输入流
        String realpath = ResourceUtils.getURL("classpath:").getPath() + partRealName;
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realpath,fileName));
        //附件下载
        httpServletResponse.setHeader("content-disposition","inline;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
        //获取响应输出流
        ServletOutputStream os = httpServletResponse.getOutputStream();
        List list = new ArrayList<>();
        list.add(is);
        list.add(os);
        return list;
    }
    //显示文件
    public static List showFile(String fileName, String partRealName, HttpServletResponse httpServletResponse) throws IOException {
        //根据文件信息中文件名字 和 文件的存储路径获取文件输入流
        String realpath = ResourceUtils.getURL("classpath:").getPath() + partRealName;
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realpath,fileName));
        //附件下载
        httpServletResponse.setHeader("content-disposition","inline;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
        //获取响应输出流
        ServletOutputStream os = httpServletResponse.getOutputStream();
        List list = new ArrayList<>();
        list.add(is);
        list.add(os);
        return list;
    }
}
