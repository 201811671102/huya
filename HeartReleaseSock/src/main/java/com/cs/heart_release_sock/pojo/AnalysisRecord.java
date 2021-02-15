package com.cs.heart_release_sock.pojo;

import java.util.Date;

public class AnalysisRecord {
    private Integer id;

    private Date recordTime;

    private Integer userId;

    private Integer analysisId;

    private Double score;

    public AnalysisRecord() {
    }

    public AnalysisRecord(Date recordTime, Integer userId, Integer analysisId, Double score) {
        this.recordTime = recordTime;
        this.userId = userId;
        this.analysisId = analysisId;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}