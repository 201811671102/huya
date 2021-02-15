package pre.cg.shiro.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pre.cg.shiro.jwt.inter.JWTAuthInterceptor;

/**
 * @ClassName InterceptorConfig
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/15 19:14
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor())
                .addPathPatterns("/**");
    }
    @Bean
    public JWTAuthInterceptor jwtAuthInterceptor(){
        return new JWTAuthInterceptor();
    }
}
