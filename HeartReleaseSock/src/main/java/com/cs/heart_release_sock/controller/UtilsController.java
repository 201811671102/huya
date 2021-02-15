package com.cs.heart_release_sock.controller;

import com.cs.heart_release_sock.base.dto.ResultDTO;
import com.cs.heart_release_sock.base.utils.ResultUtils;
import com.cs.heart_release_sock.base.utils.ZegouUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName UtilsController
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/6 20:16
 **/
@RequestMapping("/utils")
@Controller
@Api(tags = {"工具类接口"})
public class UtilsController {

    @PostMapping("/zegouLogin")
    @ResponseBody
    @ApiOperation(value = "Zegou登录")
    public ResultDTO<String> zegouLogin(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)String uid
    ){
        String zeGouToken = ZegouUtils.getInstance().getZeGouToken(uid);
        return ResultUtils.success(zeGouToken);
    }


}
