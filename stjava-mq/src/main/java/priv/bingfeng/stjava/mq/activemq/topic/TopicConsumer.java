package priv.bingfeng.stjava.mq.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 非持久订阅者
// 非持久订阅只有当客户端处于连接状态才能收到发送到某个主题的消息，
// 而当客户端处于离线状态，这个时间段发到主题的消息它永远不会收到
public class TopicConsumer {
    public static void main(String[] args) {
        new ConsumerThread("tcp://localhost:61616", "topic1").start();
        new ConsumerThread("tcp://localhost:61616", "topic1").start();
    }
}


class ConsumerThread extends Thread {

    private String brokerUrl;
    private String destinationUrl;

    ConsumerThread(String brokerUrl, String destinationUrl) {
        this.brokerUrl = brokerUrl;
        this.destinationUrl = destinationUrl;
    }

    @Override
    public void run() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerUrl);

        try (Connection conn = connectionFactory.createConnection()) {
            conn.start();

            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 订阅topic, 一条消息，多个订阅者接收
            Destination destination = session.createTopic(destinationUrl);

            MessageConsumer consumer = session.createConsumer(destination);

            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) message).getText());
            } else {
                System.out.println(message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
