package com.chuangshu.studentworker.controller;


import com.chuangshu.studentworker.pojo.Worker;
import com.chuangshu.studentworker.service.WorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Jiaqi Guo
 * @date 2020/6/10  - 22:02
 */
@RestController
@ResponseBody
@RequestMapping("/worker")
@Api(value = "报销系统controller",tags = {"报销访问接口"})
public class WorkerController {

    @Resource
    private WorkerService workerService;

    @ApiOperation(value = "职工注册")
    @PostMapping("/register")
    public String register(@ApiParam("姓名")  @RequestParam("name") String name,
                           @ApiParam("职工号") @RequestParam("worker_number") Integer worker_number,
                           @ApiParam("密码") @RequestParam("password") String password,
                           @ApiParam("职务") @RequestParam("position") String position,
                           @ApiParam("身份证") @RequestParam("body_number") Integer body_number){
        //判断一下该用户是否已经存在，这里只需要检测账号密码是否已经存在，则表示该用户已经存在
        if (workerService.ifNull(worker_number, password) != null) {
            //说明该账号已经存在
            return "说明该账号已经存在";
        } else {
            Worker worker = new Worker();
            worker.setName(name);
            worker.setWorker_number(worker_number);
            worker.setPassword(password);
            worker.setPosition(position);
            worker.setBody_number(body_number);

            int register = workerService.register(worker);
            if (register == 1) {
                return "注册成功";
            } else {
                return "注册失败";
            }
        }


    }

    @ApiOperation(value = "职工登录")
    @PostMapping("/login")
    public String login(@ApiParam("职工号") @RequestParam("worker_number") Integer worker_number,
                        @ApiParam("密码") @RequestParam("password") String password,
                        HttpSession httpSession){

        Worker worker = workerService.login(worker_number, password);
        if (worker == null) {
            return "学号或密码输入有误";
        } else {
            httpSession.setAttribute("worker", worker);
            return "登录成功";
        }

    }
}
