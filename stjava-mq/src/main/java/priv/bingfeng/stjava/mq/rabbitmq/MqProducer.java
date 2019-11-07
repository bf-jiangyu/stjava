package priv.bingfeng.stjava.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MqProducer {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("whoareyou");
        factory.setVirtualHost("study");

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection("生产者");

            channel = connection.createChannel();
            channel.basicQos(1);

//            channel.queueDeclare("queue1", true, false, false, null);

            String message = "Hello World!";

//            channel.basicPublish("", "queue1", null, message.getBytes());

//            channel.basicPublish("exchange-topic", "com.sms.create", null, message.getBytes());


            // 定义交换器
            channel.exchangeDeclare("ps_test", BuiltinExchangeType.FANOUT);
            // 定义临时队列（排他队列），链接关闭后会自动删除。
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, "ps_test", "");
            channel.basicPublish("ps_test", "", null, message.getBytes());


            System.out.println("消息已发送！");

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
