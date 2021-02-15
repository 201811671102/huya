package com.cs.heart_release_sock.base.utils;

import com.cs.heart_release_sock.base.config.BaiDuApiConfig;
import com.cs.heart_release_sock.manager.SchedulerManager;
import org.json.JSONObject;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BaiDuApiUtil
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/9 17:21
 **/
@Configuration
public class BaiDuApiUtil {
    @Autowired
    SchedulerManager schedulerManager;

    public static BaiDuApiUtil baiDuApiUtil;
    @PostConstruct
    public void init(){baiDuApiUtil = this;}


    public static Date getAuth(){
        Map<String,String> param = new HashMap<>();
        param.put("grant_type","client_credentials");
        param.put("client_id", BaiDuApiConfig.apiKey);
        param.put("client_secret",BaiDuApiConfig.secretKey);
        String result = HttpClientUtil.doPost("https://aip.baidubce.com/oauth/2.0/token?", param);
        JSONObject jsonObject = new JSONObject(result);
        String access_token = jsonObject.getString("access_token");
        Long expires_in = jsonObject.getLong("expires_in");
        Date date = new Date(new Date().getTime()+(expires_in*1000)-1000*30);
        BaiDuApiConfig.getInstance().setAccessToken(access_token);
        return date;
    }

}
