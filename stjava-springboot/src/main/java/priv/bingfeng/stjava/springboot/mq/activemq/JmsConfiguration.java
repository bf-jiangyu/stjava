package priv.bingfeng.stjava.springboot.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfiguration {

    @Bean
    public ConnectionFactory connectionFactory(
            @Value("${spring.activemq.broker-url}") String brokerUrl,
            @Value("${spring.activemq.user}") String userName,
            @Value("${spring.activemq.password}") String password) {
        return new ActiveMQConnectionFactory(userName, password, brokerUrl);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactoryQueue(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

}
