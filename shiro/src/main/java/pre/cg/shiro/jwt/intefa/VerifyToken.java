package pre.cg.shiro.jwt.intefa;

import java.lang.annotation.*;

/**
 * @ClassName VerifyToken
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/15 17:57
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyToken {
    boolean requited() default true;
}
