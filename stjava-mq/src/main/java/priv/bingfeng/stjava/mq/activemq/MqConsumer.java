package priv.bingfeng.stjava.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MqConsumer {

    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");;
        try (Connection conn = factory.createConnection("admin", "admin")) {
            conn.start();

            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("queue1");

            MessageConsumer consumer = session.createConsumer(destination);

            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                System.out.println("收到文本消息:" + ((TextMessage) message).getText());
            } else {
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
