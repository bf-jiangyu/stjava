package priv.bingfeng.stjava.base.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MqProducer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory factory;
        Connection conn = null;
        Session session;
        try {
            factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
            conn = factory.createConnection();
            conn.start();

            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("queue1");
            MessageProducer producer = session.createProducer(destination);

            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String text = "Hello world!";
            TextMessage message = session.createTextMessage(text);

            producer.send(message);
        } catch (JMSException e) {
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
