package com.chuangshu.studentworker.serviceimpl;


import com.chuangshu.studentworker.mapper.WorkerMapper;
import com.chuangshu.studentworker.pojo.Worker;
import com.chuangshu.studentworker.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jiaqi Guo
 * @date 2020/6/10  - 21:36
 */
@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerMapper workerMapper;

    //注册
    @Override
    @Transactional
    public int register(Worker worker) {
        int insert = workerMapper.insert(worker);
        return insert;
    }

    //登录
    @Override
    @Transactional
    public Worker login(Integer worker_number,String password) {
        Worker worker = workerMapper.selectByTwo(worker_number,password);
        return worker;
    }

    @Override
    public Worker ifNull(Integer worker_number, String password) {
        Worker worker = workerMapper.ifNull(worker_number, password);
        return worker;
    }
}
