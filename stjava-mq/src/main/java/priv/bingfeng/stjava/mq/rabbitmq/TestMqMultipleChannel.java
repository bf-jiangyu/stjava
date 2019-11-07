package priv.bingfeng.stjava.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestMqMultipleChannel {

    private static final Log LOG = LogFactory.getLog(TestMqMultipleChannel.class);

    private static class TestConsumerThread implements Runnable {

        private Connection connection;
        private String queue;
        private String threadName;

        public TestConsumerThread(Connection connection, String queue, String threadName) {
            this.connection = connection;
            this.queue = queue;
            this.threadName = threadName;
        }

        public void run() {
            Channel channel;

            try {
                channel = connection.createChannel();

                channel.queueDeclare(queue, true, false, false, null);
                channel.basicQos(1);

                Channel finalChannel = channel;
                channel.basicConsume(queue, false, (consumerTag, message) -> {
                    LOG.info(threadName + "-" + queue + "-收到消息：" + new String(message.getBody(), StandardCharsets.UTF_8));

                    finalChannel.basicAck(message.getEnvelope().getDeliveryTag(), true);

                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, consumerTag -> {
                    System.out.println("cancel");
                    System.out.println("cancel");
                }, (consumerTag, sig) -> {
                    System.out.println("mq connection error");
                    System.out.println("mq connection error");
                });

//                System.out.println("开始接收消息");
//                System.out.println(System.in.read());

            } catch (IOException e) {
                System.out.println("链接异常");
                e.printStackTrace();
//            } finally {
//                if (channel != null) {
//                    System.out.println("channel is open: " + channel.isOpen());
//                    if (channel.isOpen()) {
//                        try {
//                            System.out.println("close channel");
//                            channel.close();
//                        } catch (IOException | TimeoutException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                if (connection != null) {
//                    System.out.println("connection is open: " + connection.isOpen());
//                    if (connection.isOpen()) {
//                        try {
//                            System.out.println("close connection");
//                            connection.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
            }
        }

    }

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("whoareyou");
        factory.setVirtualHost("study");

        Connection connection = null;
        try {
//            connection = factory.newConnection();
            for (int i = 0; i < 20; i ++) {
                if (i % 8 == 0)
                    connection = factory.newConnection();
                new Thread(new TestConsumerThread(connection, "queue" + (i % 4), "thread" + i)).start();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
