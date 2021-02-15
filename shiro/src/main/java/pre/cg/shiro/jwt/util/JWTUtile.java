package pre.cg.shiro.jwt.util;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName JWTUtile
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/15 19:18
 **/
public class JWTUtile {
    public static String createJWT(String uid){
        //Signature
        Algorithm algorithm = Algorithm.HMAC256("cg");
        //Header
        Map<String, Object> headerClaims = new HashMap();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
        String token = com.auth0.jwt.JWT.create()
                .withHeader(headerClaims)
                //Payload
                .withIssuer("CG")//发布者
                .withSubject("CGTestJWT")//主题
                .withAudience("user")//观众 接受者
                .withIssuedAt(new Date())//生成签名时间
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*10))//有效时间
                .withNotBefore(new Date())//生效时间
                .withJWTId(UUID.randomUUID().toString())//编号
                .withClaim("uid",uid)//自定义参数
                .sign(algorithm);
        return token;
    }
}
