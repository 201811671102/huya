package com.cs.heart_release_sock.code;

/**
 * @ClassName BaseExceptionCode
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/17 0:45
 **/
public enum BaseCode {

    Success(200,"成功"),
    Null(404,"查无数据"),
    System_Error(500,"系统错误"),
    FailOperation(505,"操作错误");

    private Integer code;
    private String message;
    BaseCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
