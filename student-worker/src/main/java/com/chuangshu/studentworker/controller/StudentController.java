package com.chuangshu.studentworker.controller;



import com.chuangshu.studentworker.pojo.Student;
import com.chuangshu.studentworker.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 23:09
 */
@RestController
@ResponseBody
@Api(value = "报销系统controller",tags = {"报销访问接口"})
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;


    @ApiOperation(value = "学生注册")
    @PostMapping("/register")
    public String register(@ApiParam("姓名") @RequestParam("name") String name,
                           @ApiParam("学号") @RequestParam("student_number") Integer student_number,
                           @ApiParam("密码") @RequestParam("password") String password,
                           @ApiParam("学院") @RequestParam("college") String college,
                           @ApiParam("班级") @RequestParam("stu_class") String stu_class,
                           @ApiParam("身份证") @RequestParam("body_number") Integer body_number){
        /*
        * 判断字段是否为空
        * */
        //判断一下该用户是否已经存在，这里只需要检测账号密码是否已经存在，则表示该用户已经存在
        if (studentService.ifNull(student_number,password)!=null){
            //说明该账号已经存在
            return "说明该账号已经存在";
        }else {
            Student student = new Student();
            student.setName(name);
            student.setStudent_number(student_number);
            student.setPassword(password);
            student.setCollege(college);
            student.setStu_class(stu_class);
            student.setBody_number(body_number);
            int register = studentService.register(student);
            System.out.println("学生"+ student);
            if(register==1){
                return "注册成功";
            }else{
                return "注册失败";
            }
        }

    }

    @ApiOperation(value = "学生登录")
    @PostMapping("/login")
    public String login(@ApiParam("学号") @RequestParam("student_number") Integer student_number,
                        @ApiParam("密码") @RequestParam("password") String password,
                        HttpSession httpSession){

        Student student = studentService.login(student_number, password);
        if(student==null){
            return "学号或密码输入有误";
        }else {
            httpSession.setAttribute("student",student);
            return "登录成功";
        }

    }
}
