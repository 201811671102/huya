package pre.cg.rabbitmqc.rabbitMQ;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName receiver
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/2 7:43
 **/
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.CG.queue.profile}",autoDelete = "true"),
                exchange = @Exchange(value = "${mq.CG.exchange}",type = ExchangeTypes.DIRECT),
                key = "${mq.CG.queue.routing.profile}"
        )
)
public class receiver {
    @RabbitHandler
    public void  process(String msg){
        System.out.println(msg);
    }
}
