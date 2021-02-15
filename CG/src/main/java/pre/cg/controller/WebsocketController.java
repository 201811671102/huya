package pre.cg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pre.cg.base.ResultUtil;
import pre.cg.base.dto.ResultDTO;
import pre.cg.pojo.User;
import pre.cg.service.UserService;
import pre.cg.websocket.CGWebSocketServer;

/**
 * @ClassName WebsocketController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/3/26 11:26
 **/
@Controller
@RequestMapping("/websock")
@Api(value = "聊天")
public class WebsocketController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/login")
    @ApiOperation(value="登录")
    public ResultDTO login(
            @ApiParam(value = "账号",required = true)@RequestParam(value = "account",required = true)String account,
            @ApiParam(value = "密码",required = true)@RequestParam(value = "password",required = true)String passwrod){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account,passwrod);
        try {
            subject.login(token);
            return new ResultUtil().Success(userService.SelectOne("account",account));
        }catch (UnknownAccountException e){
            /**
             * 登录失败，不存在用户名
             */
            return new ResultUtil().Error("401","不存在用户名为的用户");
        }catch (IncorrectCredentialsException e){
            /**
             * 登录失败，密码错误
             */
            return new ResultUtil().Error("401","密码错误");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultUtil().Error("401","密码错误");
        }
    }
    @Autowired
    private CGWebSocketServer webSocketServer;

    @PostMapping("/sendMessage")
    @ResponseBody
    @ApiOperation(value = "发送信息给特定用户",notes = "500报错")
    public ResultDTO sendMessage(
            @ApiParam(value = "发送者id",required = true)@RequestParam(value = "fromid",required = true)Integer fromid,
            @ApiParam(value = "接收者id",required = true)@RequestParam(value = "toid",required = true) Integer toid,
            @ApiParam(value = "信息",required = true)@RequestParam(value = "message",required = true)String message){
        try {
            webSocketServer.sendMessage(message,fromid,toid);
            return new ResultUtil().SUccess();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultUtil().Error("500",e.toString());
        }

    }
}
