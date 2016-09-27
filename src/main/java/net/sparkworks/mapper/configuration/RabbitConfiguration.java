package net.sparkworks.mapper.configuration;

import net.sparkworks.mapper.listener.ReceiverService;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.server}")
    String rabbitServer;
    @Value("${rabbitmq.port}")
    String rabbitPort;
    @Value("${rabbitmq.username}")
    String rabbitUser;
    @Value("${rabbitmq.password}")
    String rabbitPassword;
    @Value("${rabbitmq.queue.receive}")
    String rabbitQueueReceive;
    
    @Autowired
    ReceiverService receiverService;
    
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitServer);
        connectionFactory.setPort(Integer.parseInt(rabbitPort));
        connectionFactory.setUsername(rabbitUser);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }
    
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(rabbitQueueReceive);
        container.setMessageListener(receiverService);
        return container;
    }
}