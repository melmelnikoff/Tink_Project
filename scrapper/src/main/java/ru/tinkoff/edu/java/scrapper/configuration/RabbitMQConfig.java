package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMQConfig {

    private final ApplicationConfig applicationConfig;
    private final ConnectionFactory connectionFactory;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(applicationConfig.exchangeName(), false, false);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable(applicationConfig.queueName())
            .withArgument("x-dead-letter-exchange", applicationConfig.exchangeName() + ".dlx")
            .build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
            .to(directExchange())
            .with(applicationConfig.queueName());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareExchange(directExchange());
        admin.declareQueue(queue());
        admin.declareBinding(binding());
        return admin;
    }
}
