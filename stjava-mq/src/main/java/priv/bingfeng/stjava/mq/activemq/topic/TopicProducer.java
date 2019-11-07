package priv.bingfeng.stjava.mq.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducer {
    public static void main(String[] args) {
        new ProducerThread("tcp://localhost:61616", "topic1").start();
    }

    static class ProducerThread extends Thread {
        String brokerUrl;
        String destinationUrl;

        ProducerThread(String brokerUrl, String destinationUrl) {
            this.brokerUrl = brokerUrl;
            this.destinationUrl = destinationUrl;
        }

        @Override
        public void run() {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

            try (Connection conn = connectionFactory.createConnection()) {
                conn.start();

                Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Destination destination = session.createTopic(destinationUrl);

                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                String text = "Hello world!";
                TextMessage message = session.createTextMessage(text);

                producer.send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
