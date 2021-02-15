package pre.cg.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import pre.cg.pojo.User;
import pre.cg.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionDAO sessionDAO;
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //获取session中的用户
        User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //获取用户输入的token
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        User user = userService.SelectOne("account",token.getUsername());

        if (user == null){
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }
}
