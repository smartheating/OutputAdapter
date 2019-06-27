package de.smartheating.outputAdapter.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.smartheating.outputAdapter.rabbitmq.MessageConsumer;

@Configuration
public class RabbitMQConfig {

	public final static String RABBITMQ_QUEUE = "outputadapter";
	public final static String RABBITMQ_EXCHANGE = "outputadapter-exchange";
	public final static String RABBITMQ_ROUTINGKEY = "to.outputadapter";
	
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }
    
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
	
    @Bean
    Queue queue() {
        return new Queue(RABBITMQ_QUEUE, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(RABBITMQ_EXCHANGE, true, false);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RABBITMQ_ROUTINGKEY);
    }
    
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RABBITMQ_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
    @Bean
    MessageListenerAdapter listenerAdapter(MessageConsumer consumer, MessageConverter messageConverter) {
    	MessageListenerAdapter adapter = new MessageListenerAdapter(consumer, messageConverter);
    	adapter.setDefaultListenerMethod("consumeEvent");
        return adapter;
    }
   
}
