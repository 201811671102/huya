package com.chuangshu.studentworker.service;


import com.chuangshu.studentworker.pojo.Student;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 22:25
 */
public interface StudentService {
    //注册
    int register(Student student);
    Student ifNull(Integer student_number,String password);
    //登录
    Student login(Integer student_number, String password);
}
