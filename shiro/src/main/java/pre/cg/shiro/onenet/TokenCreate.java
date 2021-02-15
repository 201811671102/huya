package pre.cg.shiro.onenet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TokenCreate
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/17 14:28
 **/
public class TokenCreate {

    public  String tokencreate(String version, String res, String et, String signatureMethod, String accessKey) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        String sign = signCreate(et,res,version,accessKey,signatureMethod);
        stringBuilder
                .append("version=")
                .append(version)
                .append("&res=")
                .append(URLEncoder.encode(res,"UTF-8"))
                .append("&et=")
                .append(et)
                .append("&method=")
                .append(signatureMethod)
                .append("&sign=")
                .append(URLEncoder.encode(sign,"UTF-8"));
        return stringBuilder.toString();
    }

    public String signCreate(String et,String res,String version,String accessKey,String signatureMethod ) throws Exception{
        String encryptText = et+"\n"+signatureMethod+"\n"+res+"\n"+version;
        //Hmac 指定一个密钥算法名称
        SecretKeySpec signinKey = new SecretKeySpec(Base64.getDecoder().decode(accessKey),"Hmac"+signatureMethod.toUpperCase());
        Mac mac = Mac.getInstance("Hmac"+signatureMethod.toUpperCase());
        mac.init(signinKey);
        byte[] bytes = mac.doFinal(encryptText.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void main(String[] args) throws Exception {
        TokenCreate tokenCreate = new TokenCreate();
        String token = tokenCreate.tokencreate("2018-10-31","products/364948",System.currentTimeMillis() / 1000 + 100 * 24 * 60 * 60 + "","SHA1".toLowerCase(),"wNvceU4pVdWTDgLTr+lTrYaT6UamA+b+0W5exAO/L3g=");
        System.out.println(token);
        Map<String,String> map = new HashMap<>();
        map.put("Authorization",token);
        String result = HttpClientUtil.getInstance().doGet("http://api.heclouds.com/devices/615829896",null,map);
        System.out.println(result);
    }
}
