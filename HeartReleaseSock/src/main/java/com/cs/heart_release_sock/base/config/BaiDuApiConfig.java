package com.cs.heart_release_sock.base.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName BaiDuApiConfig
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/9 17:19
 **/
@Component
public class BaiDuApiConfig {
    public static final String apiKey = "GPv1XEHtV52PXeRGvaZ9GSut";
    public static final String secretKey = "MsHbh6upU5fsPZGp9G04XSE9GK5aeK1t";
    public static final String analysisUrl = "https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?charset=UTF-8&access_token=";
    public static String accessToken = "";

    private static class BaiDuApiConfigHolder{
        private static final BaiDuApiConfig instance = new BaiDuApiConfig();
    }
    public BaiDuApiConfig() {}
    public static BaiDuApiConfig getInstance(){return BaiDuApiConfigHolder.instance;}
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAnalysisUrl(){
        if (StringUtils.isNotBlank(analysisUrl)){
            return analysisUrl+accessToken;
        }
        return null;
    }
}
