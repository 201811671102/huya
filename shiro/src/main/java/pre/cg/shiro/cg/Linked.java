package pre.cg.shiro.cg;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * @ClassName Linked
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/18 16:21
 **/
public class Linked {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i=0;i<5;i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("1");
                }
            });
        }
        service.shutdown();
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
    }
}
