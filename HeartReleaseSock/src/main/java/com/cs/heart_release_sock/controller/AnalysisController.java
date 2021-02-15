package com.cs.heart_release_sock.controller;

import com.cs.heart_release_sock.base.config.BaiDuApiConfig;
import com.cs.heart_release_sock.base.dto.ResultDTO;
import com.cs.heart_release_sock.base.utils.HttpClientUtil;
import com.cs.heart_release_sock.base.utils.ResultUtils;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import com.cs.heart_release_sock.pojo.Analysis;
import com.cs.heart_release_sock.service.AnalysisService;
import io.swagger.annotations.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName AnalysisController
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/15 12:17
 **/
@Controller
@Api(tags = {"情感分析操作接口"})
@RequestMapping("/analysis")
public class AnalysisController {
    @Autowired
    AnalysisService analysisService;

    @PostMapping("/analysisText")
    @ResponseBody
    @Transactional
    @ApiResponses({
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 500,message = "系统错误",response = HeartReleaseException.class)
    })
    @ApiOperation(value = "分析消息")
    public ResultDTO<String> analysisText(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "分析消息",required = true)@RequestParam(value = "content",required = true)String content
    ){
        JSONObject jsonObjectInput = new JSONObject();
        jsonObjectInput.put("text",content);
        String s = HttpClientUtil.doPostJson(BaiDuApiConfig.getInstance().getAnalysisUrl(), jsonObjectInput.toString());
        JSONObject jsonObjectResult = new JSONObject(s);
        String items = jsonObjectResult.getJSONArray("items").get(0).toString();
        JSONObject jsonObjectItem = new JSONObject(items);
        Analysis analysis = new Analysis(content,uid,jsonObjectItem.getInt("sentiment"),jsonObjectItem.getDouble("confidence"));
        analysisService.insert(analysis);
        return ResultUtils.success();
    }
}
