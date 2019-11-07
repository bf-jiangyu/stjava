package priv.bingfeng.stjava.mq.activemq.transport;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerAndProducerUDP {

    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory("udp://localhost:61616");
        try (Connection conn = factory.createConnection("admin", "admin")) {
            conn.start();

            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("queue1");

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String text = "Hello world";
            Message message = session.createTextMessage(text);

            producer.send(message);

            MessageConsumer consumer = session.createConsumer(destination);

            Message consumerMessage = consumer.receive();
            if (consumerMessage instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) consumerMessage).getText());
            } else {
                System.out.println(consumerMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
