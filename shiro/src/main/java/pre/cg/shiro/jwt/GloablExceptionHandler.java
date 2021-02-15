package pre.cg.shiro.jwt;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @ClassName GloablExceptionHandler
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/15 18:51
 **/
@ControllerAdvice
public class GloablExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e){
        return "error";
    }
}
