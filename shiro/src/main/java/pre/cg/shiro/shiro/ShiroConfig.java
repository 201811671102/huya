package pre.cg.shiro.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfig
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/14 20:39
 **/
@Configuration
public class ShiroConfig {

    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordEcord.ALGORITHM_NAME);//散列算法
        hashedCredentialsMatcher.setHashIterations(PasswordEcord.HASH_ITERATIONS);//散列次数
        return hashedCredentialsMatcher;
    }
    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());//
        return shiroRealm;
    }
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录路径
   //     shiroFilterFactoryBean.setLoginUrl("/login");
        //设置错误页面,认证不通过跳转
   //     shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        //设置认证通过后页面
   //     shiroFilterFactoryBean.setSuccessUrl("/index");

        Map<String, String> map = new HashMap<String, String>();
        map.put("/**", "anon");
        map.put("/cg/user", "roles[user]");
        map.put("/cg/manager", "roles[manager]");
        map.put("/cg/custem", "roles[custem]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

}
