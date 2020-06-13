package com.chuangshu.studentworker.pojo;


import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 12:29
 */
@Table(name= "t_worker")
public class Worker {
    @Id
    private Integer id;
    private String name;
    private Integer worker_number;
    private String password;
    private String position;//职务
    private Integer body_number;

    public Worker(Integer id, String name, Integer worker_number, String password, String position, Integer body_number) {
        this.id = id;
        this.name = name;
        this.worker_number = worker_number;
        this.password = password;
        this.position = position;
        this.body_number = body_number;
    }

    public Worker() {
    }

    @Override
    public String toString() {
        return "worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", worker_number=" + worker_number +
                ", password='" + password + '\'' +
                ", position='" + position + '\'' +
                ", body_number=" + body_number +
                '}';
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

    public Integer getWorker_number() {
        return worker_number;
    }

    public void setWorker_number(Integer worker_number) {
        this.worker_number = worker_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getBody_number() {
        return body_number;
    }

    public void setBody_number(Integer body_number) {
        this.body_number = body_number;
    }
}
