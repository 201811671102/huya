package com.cs.heart_release_sock.base.utils;

import com.cs.heart_release_sock.code.BaseCode;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName IOUtil
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/17 15:42
 **/
public class EncodeUtil {
    private static class EncodeUtilHolder{
        private static final EncodeUtil instance = new EncodeUtil();
    }

    public EncodeUtil(){}
    public static EncodeUtil getInstance(){
        return EncodeUtil.EncodeUtilHolder.instance;
    }

    /*
    * base64加密图片
    * */
    public String encodeImage(InputStream inputStream,String type){
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            BufferedImage read = ImageIO.read(inputStream);
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(read,type,byteArrayOutputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(byteArrayOutputStream.toByteArray());
        }catch (Exception e){
            throw new HeartReleaseException(BaseCode.System_Error.getCode(),"base64加密图片出错！\n "+e.getMessage());
        }
    }
    /*
    * base64解密图片
    * */

    public InputStream decodeImage(String base64){
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] bytes = base64Decoder.decodeBuffer(base64);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            return byteArrayInputStream;
        } catch (IOException e) {
            throw new HeartReleaseException(BaseCode.System_Error.getCode(),"base64解密图片出错！ \n "+e.getMessage());
        }
    }
}
