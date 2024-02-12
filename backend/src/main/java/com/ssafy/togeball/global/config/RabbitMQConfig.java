package com.ssafy.togeball.global.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.chat.queue}")
    private String chatQueue;

    @Value("${rabbitmq.notification.queue}")
    private String notificationQueue;

    @Value("${rabbitmq.chat.routing-key}")
    private String chatRoutingKey;

    @Value("${rabbitmq.matching.queue}")
    private String matchingQueue;

    @Value("${rabbitmq.matching.routing-key}")
    private String matchingRoutingKey;

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private int port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue chatQueue() {
        return new Queue(chatQueue, false);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, false);
    }

    @Bean
    public Binding chatBinding(Queue chatQueue, DirectExchange exchange) {
        return BindingBuilder.bind(chatQueue).to(exchange).with(chatRoutingKey);
    }

    @Bean
    public Queue matchingQueue() {
        return new Queue(matchingQueue, false);
    }

    @Bean
    public Binding matchingBinding(Queue matchingQueue, DirectExchange exchange) {
        return BindingBuilder.bind(matchingQueue).to(exchange).with(matchingRoutingKey);
    }

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
