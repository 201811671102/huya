package pre.cg.ws;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@EnableRabbit
public class WsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }

}
