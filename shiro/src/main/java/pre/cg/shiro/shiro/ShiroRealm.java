package pre.cg.shiro.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import pre.cg.shiro.data.UserData;
import pre.cg.shiro.data.enpty.Role;
import pre.cg.shiro.data.enpty.User;

/**
 * @ClassName ShiroRealm
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/14 20:40
 **/
public class ShiroRealm extends AuthorizingRealm {
    /*
    * 授权
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = (String) principalCollection.getPrimaryPrincipal();
        User user = new User();
        switch (account){
            case "123":user= UserData.getUser();break;
            case "456":user= UserData.getManage();break;
            case "789":user= UserData.getCustem();break;
            default:user = null;
        }
        for (Role role : user.getRoleList()){
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (String permission:role.getPermission()){
                simpleAuthorizationInfo.addStringPermission(permission);
            }
        }
        return simpleAuthorizationInfo;
    }
    /*
    * 认证
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String account = (String) authenticationToken.getPrincipal();
        User user = new User();
        switch (account){
            case "123":user= UserData.getUser();break;
            case "456":user= UserData.getManage();break;
            case "789":user= UserData.getCustem();break;
            default:user = null;
        }
        if (user == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getAccount(),user.getPassword(),
                ByteSource.Util.bytes(user.getAccount()+user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
