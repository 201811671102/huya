package com.chuangshu.studentworker.serviceimpl;


import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.chuangshu.studentworker.mapper.StudentMapper;
import com.chuangshu.studentworker.pojo.Student;
import com.chuangshu.studentworker.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 22:28
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    @Transactional
    //注册
    public int register(Student student) {
        int insert = studentMapper.insert(student);
        return insert;
    }

    //学生注册时判断注册的用户是否已经注册该账号
    @Override
    @Transactional
    public Student ifNull(Integer student_number,String password) {
        Student student = studentMapper.ifNull(student_number, password);

        return student;
    }

    @Override
    @Transactional
    //登录
    public Student login(Integer student_number, String password) {
        Student select = studentMapper.selectbytwo(student_number, password);
        return select;
    }
}
