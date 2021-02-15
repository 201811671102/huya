package pre.cg.ftp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pre.cg.ftp.base.DTO.ResultDTO;
import pre.cg.ftp.base.ResultUtils;
import pre.cg.ftp.base.code.Code;
import pre.cg.ftp.pojo.User;
import pre.cg.ftp.service.UserService;

/**
 * @ClassName ItemController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/11/26 16:48
 **/
@RequestMapping("/user")
@RestController
@Api(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "get User")
    public ResultDTO<User> getUser(
            @ApiParam(value = "account",required = true)@RequestParam(value = "account",required = true)String account,
            @ApiParam(value = "password",required = true)@RequestParam(value = "password",required = true)String password) throws Exception {
        User user = userService.getUser(account);
        if (user != null && user.getPassword().equals(password)){
            return ResultUtils.success(user);
        }else{
            return ResultUtils.error(Code.FAil.code,"登录失败");
        }
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "get User")
    public ResultDTO<User> addUser(
            @ApiParam(value = "account",required = true)@RequestParam(value = "account",required = true)String account,
            @ApiParam(value = "name",required = true)@RequestParam(value = "name",required = true)String name,
            @ApiParam(value = "password",required = true)@RequestParam(value = "password",required = true)String password,
            @ApiParam(value = "imagepath",required = true)@RequestParam(value = "imagepath",required = true)String imagepath) throws Exception {
        User user = new User();
        user.setName(name);
        user.setAccount(account);
        user.setPassword(password);
        user.setImagepath(imagepath);
        userService.addUser(user);
        return ResultUtils.success(user);
    }

}
