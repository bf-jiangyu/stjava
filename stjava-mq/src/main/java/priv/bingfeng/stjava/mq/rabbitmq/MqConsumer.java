package priv.bingfeng.stjava.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class MqConsumer {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("whoareyou");

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection("消费者");

            channel = connection.createChannel();

            channel.queueDeclare("queue1", true, false, false, null);
            channel.basicQos(1);

            Channel finalChannel = channel;
            channel.basicConsume("queue1", false, (consumerTag, message) -> {
                System.out.println(consumerTag);
                System.out.println(message.getEnvelope().getDeliveryTag());
                System.out.println("收到消息：" + new String(message.getBody(), StandardCharsets.UTF_8));

                finalChannel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }, consumerTag -> {
                System.out.println("cancel");
                System.out.println("cancel");
            }, (consumerTag, sig) -> {
                System.out.println("mq connection error");
                System.out.println("mq connection error");
            });

            System.out.println("开始接收消息");
            System.out.println(System.in.read());

        } catch (IOException | TimeoutException e) {
            System.out.println("链接异常");
            e.printStackTrace();
        } finally {
            if (channel != null) {
                System.out.println("channel is open: " + channel.isOpen());
                if (channel.isOpen()) {
                    try {
                        System.out.println("close channel");
                        channel.close();
                    } catch (IOException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (connection != null) {
                System.out.println("connection is open: " + connection.isOpen());
                if (connection.isOpen()) {
                    try {
                        System.out.println("close connection");
                        connection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
