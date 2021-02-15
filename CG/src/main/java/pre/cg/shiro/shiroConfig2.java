package pre.cg.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;



/**
 * 配置类
 */
@Configuration
public class shiroConfig2 {
   /* @Value("${spring.redis.port:#{6379}}")
    private Integer port;
    @Value("${spring.redis.host:#{'192.168.11.134'}}")
    private String host;
    @Value("${spring.redis.timeout:#{1000}}")
    private Integer timeout;*/
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("SecurityManager")SecurityManager securityManager,
            @Qualifier("KickoutSessionControlFilter")KickoutSessionControlFilter kickoutSessionControlFilter){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //自定义拦截器限制并发人数
        LinkedHashMap<String, javax.servlet.Filter> filterMap = new LinkedHashMap<>();
        //限制同一账号同时在线人数
        filterMap.put("kickout", kickoutSessionControlFilter);

        shiroFilterFactoryBean.setFilters(filterMap);


        /*
         * anon 无需认证
         * authc必须认证
         * user remeberMe功能后直接访问
         * perms 资源权限
         * role 角色权限
         * */
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("/**","kickout,anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    /**
     * 创建DefaultWebSecurityManager
     * @Qualifier("userRealm")UserRealm userRealm ：
     */
    @Bean(name = "SecurityManager")
    public SecurityManager getSecurityManager (
            @Qualifier("userRealm")UserRealm userRealm,
            @Qualifier("RedisCacheManager")RedisCacheManager redisCacheManager,
            @Qualifier("sessionManager")SessionManager sessionManager,
            @Qualifier("CookieRememberMeManager")CookieRememberMeManager cookieRememberMeManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //自定义realm
        securityManager.setRealm(userRealm);
        //redis缓存
        securityManager.setCacheManager(redisCacheManager);
        //session管理
        securityManager.setSessionManager(sessionManager);
        //记住我
        securityManager.setRememberMeManager(cookieRememberMeManager);
        return securityManager;
    }
    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
    /**
     * 必须（thymeleaf页面使用shiro标签控制按钮是否显示）
     * 未引入thymeleaf包，Caused by: java.lang.ClassNotFoundException: org.thymeleaf.dialect.AbstractProcessorDialect
     * @return
     */

    /**
     * cookie对象
     */
    @Bean(name = "SimpleCookie")
    public SimpleCookie simpleCookie(){
        //对应前端name
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }
    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     * @return
     */
    @Bean(name = "CookieRememberMeManager")
    public CookieRememberMeManager cookieRememberMeManager(@Qualifier("SimpleCookie")SimpleCookie simpleCookie){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
    /**
     * FormAuthenticationFilter 过滤器 过滤记住我
     * @return
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }

    /**
     * redis 缓存
     */
    @Bean(name = "RedisCacheManager")
    public RedisCacheManager cacheManager(
            @Qualifier("RedisManager")RedisManager redisManager){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        //redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("account");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(2000000);
        return redisCacheManager;
    }
    /**
     * 让某个实例的某个方法的返回值注入为Bean的实例
     * Spring静态注入
     * @return
     */
  /*  @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(
            @Qualifier("RedisCacheManager")RedisCacheManager redisCacheManager){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{redisCacheManager});
        return factoryBean;
    }*/

    /**
     * 配置shiro redismanager
     */
    @Bean(name = "RedisManager")
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.11.134");
        redisManager.setPort(6379);
        redisManager.setTimeout(10000);
        redisManager.setDatabase(0);
        return redisManager;
    }

    /**
     * session manager
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(
            @Qualifier("RedisSessionDAO ")RedisSessionDAO  redisSessionDAO,
            @Qualifier("sessionIdCookie")SimpleCookie sessionIdCookie,
            @Qualifier("RedisCacheManager")RedisCacheManager redisCacheManager){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        sessionManager.setCacheManager(redisCacheManager);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
    /**
     * redisSessionDAO
     */
    @Bean(name = "RedisSessionDAO ")
    public RedisSessionDAO redisSessionDAO(@Qualifier("RedisManager")RedisManager redisManager){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }
    /**
     * 限制同一账户同时登录人数控制
     */
    @Bean(name = "KickoutSessionControlFilter")
    public KickoutSessionControlFilter kickoutSessionControlFilter(
            @Qualifier("RedisCacheManager")RedisCacheManager redisCacheManager,
            @Qualifier("sessionManager")SessionManager sessionManager){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(redisCacheManager);
        kickoutSessionControlFilter.setSessionManager(sessionManager);
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl("/toLogin");
        return kickoutSessionControlFilter;
    }

    /**
     * 授权所用配置
     */
    @Bean(name = "getDefaultAdvisorAutoProxyCreator")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator  defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator ();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    /**
     *注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("SecurityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * Shiro生命周期处理器
     *
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
