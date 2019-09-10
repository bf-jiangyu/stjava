package priv.bingfeng.stjava.springboot.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Component
public class MqAReceive {

    @RabbitListener(queues="okong", containerFactory="prefetchOneRabbitListenerContainerFactory")
    public void process(byte[] message) {
        System.out.println(new String(message));
    }

}
