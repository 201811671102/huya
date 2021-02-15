package pre.cg.ftp.base;

import pre.cg.ftp.base.DTO.ResultDTO;
import pre.cg.ftp.base.code.Code;

/**
 * @ClassName ResultUtils
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/18 23:32
 **/
public class ResultUtils {
    public static  <T> ResultDTO<T> success(T data){
        return new ResultDTO<T>(Code.SUCCESS.code,"success",data);
    }
    public static  <T> ResultDTO<T> success(){
        return new ResultDTO<T>(Code.SUCCESS.code,"success",null);
    }
    public static  <T> ResultDTO<T> success(String message,T data){
        return new ResultDTO<T>(Code.SUCCESS.code,message,data);
    }
    public static  <T> ResultDTO<T> error(Integer code,String message){
        return new ResultDTO<T>(code,message,null);
    }
    public static  <T> ResultDTO<T> error(Integer code,String message,T data){
        return new ResultDTO<T>(code,message,data);
    }
}
