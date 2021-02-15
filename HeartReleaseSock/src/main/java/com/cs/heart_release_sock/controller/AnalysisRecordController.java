package com.cs.heart_release_sock.controller;

import com.cs.heart_release_sock.base.dto.ResultDTO;
import com.cs.heart_release_sock.base.utils.ResultUtils;
import com.cs.heart_release_sock.code.BaseCode;
import com.cs.heart_release_sock.dto.AnalysisDTO;
import com.cs.heart_release_sock.dto.AnalysisRecordDTO;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import com.cs.heart_release_sock.pojo.Analysis;
import com.cs.heart_release_sock.pojo.AnalysisRecord;
import com.cs.heart_release_sock.service.AnalysisRecordService;
import com.cs.heart_release_sock.service.AnalysisService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName AnalysisRecordController
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/15 15:16
 **/
@Controller
@RequestMapping("/analysisRecord")
@Api(tags = {"用户心理分析操作接口"})
public class AnalysisRecordController {
/*
    @ApiParam(value = "起始页>=0",required = true)@RequestParam(value = "offset",required = true)Integer offset,
    @ApiParam(value = "每页数据数",required = true)@RequestParam(value = "limit",required = true)Integer limit
*/

    @Autowired
    AnalysisRecordService analysisRecordService;
    @Autowired
    AnalysisService analysisService;


    @PostMapping("/analysis")
    @ResponseBody
    @Transactional
    @ApiResponses({
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "查无数据",response = HeartReleaseException.class),
            @ApiResponse(code = 500,message = "系统错误",response = HeartReleaseException.class)
    })
    @ApiOperation(value = "心理分析")
    public ResultDTO<AnalysisDTO> analysis(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid
    ){
        List<Analysis> analyses = analysisService.selectByUid(uid);
        AtomicReference<Double> sentiment = new AtomicReference<>((double) 0);
        analyses.forEach(analysis -> {
            sentiment.set(sentiment.get()+ ((analysis.getSentiment()-1) * analysis.getConfidence()) );
        });
        double sentimentall = sentiment.get()/ analyses.size();
        AnalysisDTO analysisDTO = new AnalysisDTO();
        analysisDTO.setSentiment(sentimentall);
        if (sentimentall>0){
            if (sentimentall>0.4){
                analysisDTO.setLevel("低");
            }else{
                analysisDTO.setLevel("中");
            }
        }else {
            if (sentimentall>-0.4){
                analysisDTO.setLevel("中");
            }else{
                analysisDTO.setLevel("高");
            }
        }
        AnalysisRecord analysisRecord = new AnalysisRecord(new Date(),uid,analyses.get(analyses.size()-1).getId(),sentimentall);
        analysisRecordService.insert(analysisRecord);
        return ResultUtils.success(analysisDTO);
    }


    @PostMapping("/history")
    @ResponseBody
    @Transactional
    @ApiResponses({
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "查无数据",response = HeartReleaseException.class),
            @ApiResponse(code = 500,message = "系统错误",response = HeartReleaseException.class)
    })
    @ApiOperation(value = "分析记录")
    public ResultDTO<PageInfo<AnalysisRecordDTO>> history(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "起始时间",required = true)@RequestParam(value = "dateStart",required = true) Date dateStart,
            @ApiParam(value = "截止时间",required = true)@RequestParam(value = "dateEnd",required = true) Date dateEnd,
            @ApiParam(value = "起始页>=0",required = true)@RequestParam(value = "offset",required = true)Integer offset,
            @ApiParam(value = "每页数据数",required = true)@RequestParam(value = "limit",required = true)Integer limit
    ){
        PageHelper.startPage(offset,limit);
        List<AnalysisRecordDTO> analysisRecordDTOList = analysisRecordService.selectAll(uid, dateStart, dateEnd);
        PageInfo<AnalysisRecordDTO> pageInfo = new PageInfo<>(analysisRecordDTOList);
        return ResultUtils.success(pageInfo);
    }
}
