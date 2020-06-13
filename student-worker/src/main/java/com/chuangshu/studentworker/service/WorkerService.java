package com.chuangshu.studentworker.service;


import com.chuangshu.studentworker.pojo.Worker;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 22:25
 */
public interface WorkerService {
    //注册
    int register(Worker worker);
    //登录
    Worker login(Integer worker_number, String password);

    Worker ifNull(Integer worker_number, String password);
}
