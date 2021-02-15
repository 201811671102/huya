package pre.cg.huya.base;

/**
 * @ClassName code
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/18 23:45
 **/
public enum Code {
    SUCCESS(200),
    FAil(400),
    UNFOUNDED(404),
    ERROR(500);


    public Integer code;

    Code(Integer code){
        this.code = code;
    }
}
