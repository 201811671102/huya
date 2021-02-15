package pre.cg.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pre.cg.shiro.data.enpty.User;
import pre.cg.shiro.jwt.intefa.VerifyToken;
import pre.cg.shiro.jwt.util.JWTUtile;
import pre.cg.shiro.shiro.PasswordEcord;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName Controller
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/14 21:51
 **/
@Controller
@RequestMapping("/cg")
public class LoginController {

    @GetMapping("/login")
    @ResponseBody
    public String login(User user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return JWTUtile.createJWT("1");
        }catch (UnknownAccountException e){
            return "不存在";
        }catch (IncorrectCredentialsException e){
            return "错误";
        }
    }

    @GetMapping("/user")
    @VerifyToken(requited = true)
    @ResponseBody
    public String user(){
        return "user";
    }
    @GetMapping("/manager")
    @ResponseBody
    public String manager(){
        return "manager";
    }
    @GetMapping("/custem")
    @ResponseBody
    public String custem(){
        return "custem";
    }
    @GetMapping("/insert")
    @ResponseBody
    public String insert(){
        return "insert";
    }
    @GetMapping("/delete")
    @ResponseBody
    public String delete(){
        return "delete";
    }
    @GetMapping("/select")
    @ResponseBody
    public String select(){
        return "select";
    }
    @GetMapping("/update")
    @ResponseBody
    public String update(){
        return "update";
    }


    public static void main(String[] args) {
        User user = new User();
        user.setAccount("789");
        user.setPassword("456");
        PasswordEcord passwordEcord = new PasswordEcord();
        passwordEcord.encryptPassword(user);
        System.out.println(user.getSalt());
        System.out.println(user.getPassword());
    }
}
