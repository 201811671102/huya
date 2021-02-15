package pre.cg.controller;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pre.cg.pojo.User;
import pre.cg.redis.RedisUtil;
import pre.cg.service.UserService;


@Controller
public class shirocontroller {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public String login(String account, String password){
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(account,password);
        //3.执行登录方法
        try{
            subject.login(token);
            //session
          //  Session session = SecurityUtils.getSubject().getSession();
            //登录成功
            Session session = subject.getSession();
            System.out.println("--------------------id>>"+session.getId());
            System.out.println("success");
            return "main";
        }catch (UnknownAccountException e){
            /**
             * 登录失败，不存在用户名
             */
            System.out.println("用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            /**
             * 登录失败，密码错误
             */
            System.out.println("密码错误");
            return "login";
        }
    }

    @GetMapping("/toLogin")
    public String toLogin(){ return "login.html";}
    @GetMapping("/add")
    public String add(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        return "/user/add";}
    @GetMapping("/update")
    public String update(){return "/user/update";}
    @GetMapping("/main")
    public String main(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        System.out.println("-----------------");
        System.out.println("-----------------"+user.getUname());

        return "main";
    }
    @GetMapping("/error")
    public String error(){return "error";}
    @GetMapping("/webSocket2")
    public String webSocket2(){return "webSocket2";}

}
