package com.cs.heart_release_sock.dto;

import com.cs.heart_release_sock.pojo.AnalysisRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName AnalysisRecordDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/15 17:27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisRecordDTO implements Serializable {
    private static final long serialVersionUID = 6214601076141354038L;
    private Date recordTime;
    private double sentiment;
    private String level;

    public AnalysisRecordDTO(AnalysisRecord analysisRecord) {
        this.recordTime = analysisRecord.getRecordTime();
        this.sentiment = analysisRecord.getScore();
        if (analysisRecord.getScore()>0){
            if (analysisRecord.getScore()>0.4){
                this.level = "低";
            }else{
                this.level = "中";
            }
        }else {
            if (analysisRecord.getScore()>-0.4){
                this.level = "中";
            }else{
                this.level = "高";
            }
        }
    }
}
