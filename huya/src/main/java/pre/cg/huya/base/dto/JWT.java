package pre.cg.huya.base.dto;

import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName JWT
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/11 21:43
 **/
public class JWT {
    public static void main(String[] args) {

        //Signature
        Algorithm algorithm = Algorithm.HMAC256("65f3285847dfeac7252f73d980010105");
        //Header
        Map<String, Object> headerClaims = new HashMap();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");

        int now = (int)(System.currentTimeMillis()/1000);
        String token = com.auth0.jwt.JWT.create()
                .withHeader(headerClaims)
                .withClaim("creator", "DEV")
                .withClaim("role", "P")
                .withClaim("profileId", "hy_75977419")
                .withClaim("extId", "")
                .withClaim("roomId", "22462551")
                .withClaim("userId", "hy_75977420")
                .withClaim("iat", now)
                .withClaim("exp", now+30*24*60*60)
                .withClaim("appId", "appId")
                //Payload
//                .withIssuer("CG")//发布者
//                .withSubject("CGTestJWT")//主题
//                .withAudience("user")//观众 接受者
//                .withIssuedAt(new Date())//生成签名时间
//                .withExpiresAt(new Date(now+1000*10))//有效时间
//                .withNotBefore(new Date())//生效时间
//                .withJWTId(UUID.randomUUID().toString())//编号
//                .withClaim("iss","cg")//签发人
//                .withClaim("sub","cgjwt")//主主题
//                .withClaim("aud","h5")//接受一方
//                .withClaim("exp",now+1000*10)//过期时间 毫秒
//                .withClaim("nbf",now+1000*5)//生效时间
//                .withClaim("iat",now)//签发时间
//                .withClaim("jti","1")//编号
                .sign(algorithm);
        System.out.println(token);
    }
}
