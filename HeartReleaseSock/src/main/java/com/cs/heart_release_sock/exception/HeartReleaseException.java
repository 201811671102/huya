package com.cs.heart_release_sock.exception;

import com.cs.heart_release_sock.code.BaseCode;

import java.io.Serializable;

/**
 * @ClassName ParkingBaseException
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/16 18:37
 **/
public class HeartReleaseException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 2351137404448635713L;
    private String message;
    private Integer code;
    public HeartReleaseException(){super();}
    public HeartReleaseException(String message){ super(message);this.message = message;}
    public HeartReleaseException(Integer code, String message){ super(message);this.code = code;this.message = message; }
    public HeartReleaseException(BaseCode baseCode){ super(baseCode.getMessage());this.message = baseCode.getMessage();this.code = baseCode.getCode(); }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
