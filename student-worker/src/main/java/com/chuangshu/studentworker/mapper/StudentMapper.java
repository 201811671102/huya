package com.chuangshu.studentworker.mapper;


import com.chuangshu.studentworker.pojo.Student;
import com.chuangshu.studentworker.pojo.Worker;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 13:33
 */
@Repository
public interface StudentMapper extends Mapper<Student> {
    //登录
    @Select("select * from t_student where student_number=#{student_number} and password = password")
    Student selectbytwo(Integer student_number, String password);
    //判断学生注册时，学号密码是否已经在数据库中存在
    @Select("select * from t_student where student_number=#{student_number} and password = password")
    Student ifNull(Integer student_number, String password);

}
