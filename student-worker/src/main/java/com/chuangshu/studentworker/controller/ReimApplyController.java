package com.chuangshu.studentworker.controller;


import com.chuangshu.studentworker.pojo.ReimApply;
import com.chuangshu.studentworker.pojo.Student;
import com.chuangshu.studentworker.service.ReimApplyService;
import com.chuangshu.studentworker.utils.FileUtil;
import io.swagger.annotations.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 23:10
 */
@RestController
@ResponseBody
@Api(value = "报销系统controller",tags = {"报销访问接口"})
@RequestMapping("/reimapply")
public class ReimApplyController {

    @Resource
    private ReimApplyService reimApplyService;

    @PostMapping("/reimapply")
    @ApiOperation(value = "学生报销申请")
    public String ReimApply(@ApiParam("就诊医院") @RequestParam("hospital") String hospital,
                          @ApiParam("转诊证明") @RequestBody MultipartFile f1,
                          @ApiParam("发票") @RequestBody MultipartFile f2,
                          @ApiParam("看病具体时间") @RequestParam("see_doctor_time") Date see_doctor_time,
                          HttpSession httpSession) throws IOException {

        /*
          * 将两个文件储存到本地项目中的目录
         * */
        //确定好部分路径
        String partRealName1 = "/static/referral_prove/";
        String partRealName2 = "/static/invoice";
        //调用上传文件的工具类的方法，传入文件名和部分路径，返回一个新的文件名
        String newFileName1 = FileUtil.upLoadFile(f1, partRealName1);
        String newFileName2 = FileUtil.upLoadFile(f2, partRealName2);
        //将域中的student拿出来
        Student student =(Student) httpSession.getAttribute("student");
        //获取其id
        Integer id = student.getId();
        //这个id就是申请中的外键
        ReimApply reimApply = new ReimApply(null,hospital,newFileName1,newFileName2,see_doctor_time,id);
        //将reimApply对象进行上传
        int i = reimApplyService.reimApply(reimApply);
        if(i==1){
            return "提交申请成功";
        }else{
            return "提交申请失败";
        }
    }

    //撤销全部申请
    @PostMapping("/removereimapplyall")
    @ApiOperation(value = "学生撤销自己的全部申请")
    public String removeReimApplyAll(HttpSession httpSession){
        Student student = (Student)httpSession.getAttribute("student");
        Integer student_id = student.getId();
        ReimApply reimApply = new ReimApply();
        reimApply.setStudent_id(student_id);

        int i = reimApplyService.removeReimApplyAll(reimApply);
        System.out.println(i);
        if(i==0){
            return "您没有可以删除的申请记录";
        }else if(i<0){
            return "操作失败";
        }else{
            return "成功删除"+i+"条记录";
        }
    }

    //撤销一个申请
    @PostMapping("/removereimapplyone")
    @ApiOperation(value = "学生撤销一个申请")
    public String removeReimApplyOne(@ApiParam("文件编号") @RequestParam("id") Integer id){
        ReimApply reimApply = new ReimApply();
        reimApply.setId(id);
        int removeReimApplyone = reimApplyService.removeReimApplyone(reimApply);
        if(removeReimApplyone==1){
            return "撤销成功";
        }else {
            return "撤销失败";
        }
    }
    @ApiOperation(value = "职工查看所有报销申请")
    @PostMapping("/findreimapply")
    public String findReimApply(HttpServletResponse httpServletResponse) throws IOException {
        List<ReimApply> list = reimApplyService.findReimApply();
        if(list.toString()==null){
            return "暂时没有人发起申请";
        }else{
            for (int i =1;i<list.size();i++){
                ReimApply reimApply = list.get(1);
                List list1 = FileUtil.showFile(reimApply.getReferral_prove(), "/static/referral_prove/", httpServletResponse);
                List list2 = FileUtil.showFile(reimApply.getReferral_prove(), "/static/invoice/", httpServletResponse);
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
                return reimApply.toString();

            }
            return list.toString();
        }

    }
    @ApiOperation(value = "职工进行报销审核")
    @PostMapping("/checkrecord")
    public String checkRecord(@ApiParam("是否通过") @RequestParam("bool") boolean bool,
                              @ApiParam("申请id") @RequestParam("id") Integer id){
        int i = reimApplyService.checkRecord(bool, id);
        if(i==1){
            return "审核成功";
        }else {
            return "审核失败";
        }
    }
}