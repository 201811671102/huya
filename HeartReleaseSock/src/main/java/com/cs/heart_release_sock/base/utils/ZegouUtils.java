package com.cs.heart_release_sock.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.cs.heart_release_sock.base.config.ZegouConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName ZegouUtils
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/6 20:03
 **/
public class ZegouUtils {

    private static class ZegouUtilsHolder{
        private static final ZegouUtils instance = new ZegouUtils();
    }

    public ZegouUtils() {}
    public static ZegouUtils getInstance(){return ZegouUtilsHolder.instance;}

    @Autowired
    ZegouConfig zegouConfig;

    /**
     * 拉流端获取登录token
     * @param userID //这里的userID需要和web sdk前端传入的userID一致，
    //否则校验失败(因为这里的userID是为了校验和前端传进来的userID是否一致)。
     * @return
     */
    public String getZeGouToken(String userID) {
        String appId = zegouConfig.getAppId();
        String appSign = zegouConfig.getAppSign();
        String nonce = UUID.randomUUID().toString().replaceAll("-", "");
        long time = new Date().getTime() / 1000 + 30 * 60;
        String appSign32 = new String(appSign.replace("0x", "").replace(",", "").substring(0, 32));
        if (appSign32.length() < 32) {
            return null;
        }
        String sourece = getPwd(appId + appSign32 + userID + nonce + time);
        JSONObject json = new JSONObject();
        json.put("ver", 1);
        json.put("hash", sourece);
        json.put("nonce", nonce);
        json.put("expired", time); //unix时间戳，单位为秒
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        return base64.encodeAsString(json.toString().getBytes());
    }


    /**
     * 获取MD5加密
     * @param pwd 需要加密的字符串
     * @return String字符串 加密后的字符串
     */
    public String getPwd(String pwd) {
        try {
            // 创建加密对象
            MessageDigest digest = MessageDigest.getInstance("md5");
            // 调用加密对象的方法，加密的动作已经完成
            byte[] bs = digest.digest(pwd.getBytes());
            // 接下来，我们要对加密后的结果，进行优化，按照mysql的优化思路走
            // mysql的优化思路：
            // 第一步，将数据全部转换成正数：
            String hexString = "";
            for (byte b: bs) {
                // 第一步，将数据全部转换成正数：
                int temp = b & 255;
                // 第二步，将所有的数据转换成16进制的形式
                // 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
                // 因此，需要对temp进行判断
                if (temp < 16 && temp >= 0) {
                    // 手动补上一个“0”
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {

        }
        return "";
    }
}
