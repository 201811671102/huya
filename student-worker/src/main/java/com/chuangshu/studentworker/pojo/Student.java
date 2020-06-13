package com.chuangshu.studentworker.pojo;


import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 12:08
 */

@Table(name="t_student")
public class Student {
    @Id
    private Integer id;
    private String name;
    private Integer student_number;
    private String password;
    private String college;
    private String stu_class;
    private Integer body_number;

    public Student(Integer id, String name, Integer student_number, String password, String college, String stu_class, Integer body_number) {
        this.id = id;
        this.name = name;
        this.student_number = student_number;
        this.password = password;
        this.college = college;
        this.stu_class = stu_class;
        this.body_number = body_number;
    }

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudent_number() {
        return student_number;
    }

    public void setStudent_number(Integer student_number) {
        this.student_number = student_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getStu_class() {
        return stu_class;
    }

    public void setStu_class(String stu_class) {
        this.stu_class = stu_class;
    }

    public Integer getBody_number() {
        return body_number;
    }

    public void setBody_number(Integer body_number) {
        this.body_number = body_number;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", student_number=" + student_number +
                ", password='" + password + '\'' +
                ", college='" + college + '\'' +
                ", stu_class='" + stu_class + '\'' +
                ", body_number=" + body_number +
                '}';
    }
}
