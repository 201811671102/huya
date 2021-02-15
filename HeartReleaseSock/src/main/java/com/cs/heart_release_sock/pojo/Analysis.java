package com.cs.heart_release_sock.pojo;

public class Analysis {
    private Integer id;

    private String content;

    private Integer userId;

    private Integer sentiment;

    private Double confidence;

    public Analysis() {
    }

    public Analysis(String content, Integer userId, Integer sentiment, Double confidence) {
        this.content = content;
        this.userId = userId;
        this.sentiment = sentiment;
        this.confidence = confidence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSentiment() {
        return sentiment;
    }

    public void setSentiment(Integer sentiment) {
        this.sentiment = sentiment;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}