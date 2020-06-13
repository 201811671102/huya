package com.chuangshu.studentworker.controller;


import com.chuangshu.studentworker.pojo.ReimRecord;
import com.chuangshu.studentworker.pojo.Student;
import com.chuangshu.studentworker.service.ReimRecordService;
import com.chuangshu.studentworker.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 23:10
 */
@RestController
@ResponseBody
@Api(value = "报销系统controller",tags = {"报销访问接口"})
@RequestMapping("/reimrecord")
public class ReimRecordController {

    @Resource
    private ReimRecordService reimRecordService;

    @PostMapping("/findreimrecord")
    @ApiOperation(value = "显示我的报销记录")
    public String findReimRecord(HttpSession httpSession, HttpServletResponse httpServletResponse) throws IOException {
        //从域中获取用户对象
        Student student = (Student)httpSession.getAttribute("student");
        //从用户对象中获取id字段作为 记录 中的student_id的外键
        Integer student_id = student.getId();
        //new一个reimRecord的对象
        ReimRecord reimRecord = new ReimRecord();
        //将student_id作为参数设置进去
        reimRecord.setStudent_id(student_id);
        //将所有查到的 记录 放到list集合中
        List<ReimRecord> reimRecordLists = reimRecordService.findReimRecord(reimRecord);
        //如果list不为空的话，用for一个一个取出来
        if(reimRecordLists.toString()!=null){
            //遍历 记录 集合
            Map<Integer, List<Object>> map = new HashMap();
            for(int i=0;i<reimRecordLists.size();i++){
                ReimRecord reimRecord1 = reimRecordLists.get(i);
                //获取显示的图片名称
                String fileName1 = reimRecord1.getReferral_prove();
                String fileName2 = reimRecord1.getInvoice();
                List list1 = FileUtil.showFile(fileName1, "/static/referral_prove/", httpServletResponse);
                List list2 = FileUtil.showFile(fileName2, "/static/invoice/", httpServletResponse);

                FileInputStream fileInputStream1 = (FileInputStream) list1.get(1);
                FileInputStream fileInputStream2 = (FileInputStream) list2.get(1);
                ServletOutputStream servletOutputStream1 = (ServletOutputStream) list1.get(2);
                ServletOutputStream servletOutputStream2 = (ServletOutputStream) list2.get(2);


                //获取其他元素
                String hospital = reimRecord1.getHospital();
                Date see_doctor_time = reimRecord1.getSee_doctor_time();
                Integer is_success = reimRecord1.isIs_success();
                //封装成集合
                List<Object> list =new ArrayList<>();
                list.add(0,hospital);
                list.add(1,see_doctor_time);
                System.out.println(list.toArray());
                if(is_success==1){
                    list.add(2,"已通过");
                }else{
                    list.add(2,"未通过");
                }

                //文件拷贝
                IOUtils.copy(fileInputStream1,servletOutputStream1);
                IOUtils.copy(fileInputStream2,servletOutputStream2);
                IOUtils.closeQuietly(fileInputStream1);
                IOUtils.closeQuietly(servletOutputStream1);
                IOUtils.closeQuietly(fileInputStream2);
                IOUtils.closeQuietly(servletOutputStream2);
                //return "记录"+(i+1)+" "+list.toString();
                map.put(i,list);
            }
            return map.toString();

        }else {
            return "找不到任何报销记录";
        }
    }

    @ApiOperation(value = "职工查看所有的报销记录")
    @PostMapping("/register")
    public String findReimRecord(){
        List<ReimRecord> reimRecord = reimRecordService.findReimRecord();
        if(reimRecord.toString()==null){
            return "无报销记录";
        }else{
            return reimRecord.toString();
        }
    }

    @ApiOperation(value = "职工下载一个报销记录")
    @PostMapping("/doenloadreimrecord")
    public String downloadReimRecord(@ApiParam("下载文件的id") @RequestParam("id") Integer id,
                                     HttpServletResponse httpServletResponse) throws IOException {
        ReimRecord reimRecord = reimRecordService.downloadReimRecord(id);

        if(reimRecord.toString()==null){
            return "你给的id无法匹配到任何报销记录";
        }else{
            String referral_prove = reimRecord.getReferral_prove();
            String invoice = reimRecord.getInvoice();
            //调用 工具类的 下载方法
            List list1 = FileUtil.downloadFile(referral_prove, "/static/referral_prove/", httpServletResponse);
            List list2 = FileUtil.downloadFile(invoice, "/static/invoice/", httpServletResponse);
            //获取其他元素
            String hospital = reimRecord.getHospital();
            Date see_doctor_time = reimRecord.getSee_doctor_time();
            Integer is_success = reimRecord.isIs_success();
            //封装成集合
            List<Object> list =new ArrayList<>();
            list.add(0,hospital);
            list.add(1,see_doctor_time);
            System.out.println(list.toArray());
            if(is_success==1){
                list.add(2,"已通过");
            }else{
                list.add(2,"未通过");
            }
            FileInputStream fileInputStream1 = (FileInputStream) list1.get(1);
            FileInputStream fileInputStream2 = (FileInputStream) list2.get(1);
            ServletOutputStream servletOutputStream1 = (ServletOutputStream) list1.get(2);
            ServletOutputStream servletOutputStream2 = (ServletOutputStream) list2.get(2);
            //文件拷贝
            IOUtils.copy(fileInputStream1,servletOutputStream1);
            IOUtils.copy(fileInputStream2,servletOutputStream2);
            IOUtils.closeQuietly(fileInputStream1);
            IOUtils.closeQuietly(servletOutputStream1);
            IOUtils.closeQuietly(fileInputStream2);
            IOUtils.closeQuietly(servletOutputStream2);
            return list.toString();
        }
    }

}
