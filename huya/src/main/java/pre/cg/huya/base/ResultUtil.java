package pre.cg.huya.base;


import pre.cg.huya.base.dto.ResultDTO;

public class ResultUtil {

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
