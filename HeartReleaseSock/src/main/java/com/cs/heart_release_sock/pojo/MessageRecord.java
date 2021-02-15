package com.cs.heart_release_sock.pojo;

import lombok.ToString;

import java.util.Date;
@ToString
public class MessageRecord {
    private Integer id;

    private Integer code;

    private Integer fromUserId;

    private Integer toUserId;

    private Date recordTime;

    private Boolean readed;

    private byte[] content;

    public MessageRecord() {
    }

    public MessageRecord(Integer code, Integer fromUserId, Integer toUserId, Date recordTime, Boolean readed, byte[] content) {
        this.code = code;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.recordTime = recordTime;
        this.readed = readed;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Boolean getReaded() {
        return readed;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}