package pre.cg.shiro.jwt.inter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pre.cg.shiro.jwt.intefa.VerifyToken;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @ClassName JWTAuthInterceptor
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/15 17:52
 **/
public class JWTAuthInterceptor implements HandlerInterceptor {

    private JWTVerifier verifier;
    // 在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从http请求头中取出token
        String token = request.getHeader("token");
        //如果表示映射到Controller方法直接反行
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检测是否需要验证token
        if (method.isAnnotationPresent(VerifyToken.class)){
            VerifyToken verifyToken = method.getAnnotation(VerifyToken.class);
            if (verifyToken.requited()){
                if (token == null){
                    throw new RuntimeException("该请求没有token，请先获取token");
                }
                try {
                    // jwt校验
                    Algorithm algorithm = Algorithm.HMAC256("cg");
                    this.verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String userId = decodedJWT.getClaim("uid").asString();//获取自定义参数
                    String issuer = decodedJWT.getIssuer().toString();//获取发布者
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    throw new  RuntimeException("校验失败");
                }
            }
        }
        return true;
    }
    // 在业务处理器处理请求完成之后，生成视图之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception { }
    // 在DispatcherServlet完全处理完请求之后被调用，可用于清理资源
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { }
}
