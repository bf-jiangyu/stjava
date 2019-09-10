package priv.bingfeng.stjava.springboot.mq.activemq;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
public class Consumer {

    @JmsListener(destination = "queue1")
    public void receive(String message) {
        System.out.println("收到消息：" + message);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Consumer.class).web(WebApplicationType.NONE).run(args);
    }

}
