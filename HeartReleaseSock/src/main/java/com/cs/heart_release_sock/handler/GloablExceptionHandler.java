package com.cs.heart_release_sock.handler;

import com.cs.heart_release_sock.base.dto.ResultDTO;
import com.cs.heart_release_sock.base.utils.ResultUtils;
import com.cs.heart_release_sock.code.BaseCode;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GloablExceptionHandler
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/17 1:54
 **/
@ControllerAdvice
@Log4j2
public class GloablExceptionHandler {

    @ResponseBody
    @ExceptionHandler(HeartReleaseException.class)
    public ResultDTO<String> heartReleaseException(HeartReleaseException e){
        return ResultUtils.error(e.getCode(),e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public ResultDTO<String> nullPointerException(NullPointerException e){
        return ResultUtils.error(BaseCode.Null.getCode(),BaseCode.Null.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultDTO<String> exception(Exception e){
        log.info(e.getMessage());
        return ResultUtils.error(BaseCode.System_Error.getCode(),e.getMessage(),null);
    }
}
