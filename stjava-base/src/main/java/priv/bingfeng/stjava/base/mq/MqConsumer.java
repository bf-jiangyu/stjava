package priv.bingfeng.stjava.base.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MqConsumer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory factory = null;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            conn = factory.createConnection("admin", "admin");
            conn.start();

            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("queue1");

            consumer = session.createConsumer(destination);

            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                System.out.println("收到文本消息:" + ((TextMessage) message).getText());
            } else {
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
