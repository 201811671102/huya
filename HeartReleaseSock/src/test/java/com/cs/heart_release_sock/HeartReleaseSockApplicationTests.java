package com.cs.heart_release_sock;

import com.cs.heart_release_sock.base.config.BaiDuApiConfig;
import com.cs.heart_release_sock.base.utils.BaiDuApiUtil;
import com.cs.heart_release_sock.base.utils.HttpClientUtil;
import com.cs.heart_release_sock.controller.AnalysisRecordController;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = {HeartReleaseSockApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
class HeartReleaseSockApplicationTests {

    @Autowired
    AnalysisRecordController analysisRecordController;

    @Test
    void contextLoads() throws SchedulerException, InterruptedException, JSONException {
        Date datesStart = new Date(new Date().getTime()-1000*60*60*24);
        Date datesEnd = new Date();
        System.out.println(analysisRecordController.history(1, datesStart, datesEnd, 0, 10).getData());
    }

}
