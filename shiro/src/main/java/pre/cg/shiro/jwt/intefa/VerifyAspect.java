package pre.cg.shiro.jwt.intefa;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName VerifyAspect
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/17 18:03
 **/
@Aspect
@Component
@SuppressWarnings({"unused"})
public class VerifyAspect {
    /**
     * 是指那些方法需要被执行"AOP",是由"Pointcut Expression"来描述的.
     * Pointcut可以有下列方式来定义或者通过&& || 和!的方式进行组合.
     * args()
     * @args()
     * execution()
     * this()
     * target()
     * @target()
     * within()
     * @within()
     * @annotation
     * 其中execution 是用的最多的,其格式为:
     * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
     * returning type pattern,name pattern, and parameters pattern是必须的.
     * ret-type-pattern:可以为表示任何返回值,全路径的类名等.
     * name-pattern:指定方法名,代表所以,set,代表以set开头的所有方法.
     * parameters pattern:指定方法参数(声明的类型),(…)代表所有参数,()代表一个参数,(*,String)代表第一个参数为任何值,第二个为String类型.
     */
    @Pointcut("@annotation(pre.cg.shiro.jwt.intefa.VerifyToken)")
    public void annotationPointcut(){}

    /**
     * @Before: 前置通知, 在方法执行之前执行
     * @After: 后置通知, 在方法执行之后执行 。
     * @AfterRunning: 返回通知, 在方法返回结果之后执行
     * @AfterThrowing: 异常通知, 在方法抛出异常之后
     * @Around: 环绕通知, 围绕着方法执行
     *
     * around = before+切面方法
     */

    /**
     * JoinPoint对象封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,
     * 就可以获取到封装了该方法信息的JoinPoint对象.
     * 方法名	功能
     * Signature getSignature();	获取封装了署名信息的对象,在该对象中可以获取到目标方法名,
     *                              所属类的Class等信息
     * Object[] getArgs();	获取传入目标方法的参数对象
     * Object getTarget();	获取被代理的对象
     * Object getThis();	获取代理对象
     * @param joinPoint
     */
    @Before("annotationPointcut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before---------");
    }

    /**
     * ProceedingJoinPoint对象是JoinPoint的子接口,该对象只用在@Around的切面方法中,
     * 添加了
     * Object proceed() throws Throwable //执行目标方法
     * Object proceed(Object[] var1) throws Throwable //传入的新的参数去执行目标方法
     * @param proceedingJoinPoint
     * @return
     */
    @Around("annotationPointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("around------");
        System.out.println("getSignature-----------");
        System.out.println(proceedingJoinPoint.getSignature());
        System.out.println("getArgs-----------");
        System.out.println(proceedingJoinPoint.getArgs());
        System.out.println("getTarget---------");
        System.out.println(proceedingJoinPoint.getTarget());
        System.out.println("getThis--------");
        System.out.println(proceedingJoinPoint.getThis());
        return "1111";
    }
    @After("annotationPointcut()")
    public void after(){
        System.out.println("after----");
    }

}
