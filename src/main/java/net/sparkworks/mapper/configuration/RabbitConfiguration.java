package net.sparkworks.mapper.configuration;

import net.sparkworks.mapper.listener.Receiver;
import net.sparkworks.mapper.util.MyMessageListenerAdapter;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
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
    private String queueName;


    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitServer);
        connectionFactory.setPort(Integer.parseInt(rabbitPort));
        connectionFactory.setUsername(rabbitUser);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }

    @Value("${rabbitmq.queue.receive}")
    String rabbitQueueReceive;


    @Bean
    org.springframework.amqp.core.Queue queue() {
        return new org.springframework.amqp.core.Queue(rabbitQueueReceive, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(rabbitQueueReceive);
    }

    @Autowired
    Receiver receiver;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {

        Binding b = BindingBuilder.bind(queue()).to(exchange()).with("#");
        amqpAdmin.declareBinding(b);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(rabbitQueueReceive);
        container.setMessageListener(new MyMessageListenerAdapter(receiver));
        return container;
    }
}