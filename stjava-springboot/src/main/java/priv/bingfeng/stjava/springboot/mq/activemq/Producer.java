package priv.bingfeng.stjava.springboot.mq.activemq;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;
import priv.bingfeng.stjava.springboot.RunApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class Producer implements RunApplication {

    @Resource
    private JmsTemplate jmsTemplate;

    public void execute() {
        jmsTemplate.convertAndSend("queue1", "Hello Spring 3");
    }

    public static void main(String[] args) {
        RunApplication.run(Producer.class, args);
    }

}
