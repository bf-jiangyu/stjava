package priv.bingfeng.stjava.mq.rabbitmq.clustering;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MqProducer {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = null;
        Channel channel = null;
        try {
//            String host = "192.168.1.179";
//            String host = "192.168.1.180";
            String host = "192.168.1.181";
            connection = factory.newConnection(new Address[]{
                    new Address(host, 5672),
            });

            channel = connection.createChannel();

            channel.queueDeclare("queue1", true, false, false, null);

            String message = host + ": Hello World!";
            channel.basicPublish("", "queue1", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

            System.out.println(connection.getAddress() + ":消息已发送！");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
