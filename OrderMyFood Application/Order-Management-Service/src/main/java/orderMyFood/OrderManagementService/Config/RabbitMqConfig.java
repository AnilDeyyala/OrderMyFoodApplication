package orderMyFood.OrderManagementService.Config;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.rabbitmq.client.AMQP.Queue;

@Configuration
public class RabbitMqConfig {
	
	public static final String PLACE_ORDER_QUEUE = "order_placed";
	public static final String UPDATE_ORDER_QUEUE = "update_order";
	public static final String CANCEL_ORDER_QUEUE = "cancel_order";

	public static final String EXCHANGE = "message_exchange";
	
	public static final String PLACE_ORDER_ROUTING_KEY = "order_placed_routing_key";
	public static final String UPDATE_ORDER_ROUTING_KEY = "update_order_routing_key";	
	public static final String CANCEL_ORDER_ROUTING_KEY = "cancel_order_routing_key";
	
	@Bean
	public Queue place_order_queue() {
		return new Queue(PLACE_ORDER_QUEUE);
	}
	@Bean
	public Queue update_order_queue() {
		return new Queue(UPDATE_ORDER_QUEUE);
	}
	@Bean
	public Queue cancel_order_queue() {
		return new Queue(CANCEL_ORDER_QUEUE);
	}
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}
	@Bean
	public Binding place_order_binding(@Qualifier("place_order_queue") Queue queue, TopicExchange exchange) {
		return BindingBuilder
				.bind(place_order_queue())
				.to(exchange())
				.with(PLACE_ORDER_ROUTING_KEY);
	}
	@Bean
	public Binding update_order_binding(@Qualifier("update_order_queue") Queue queue, TopicExchange exchange) {
		return BindingBuilder
				.bind(update_order_queue())
				.to(exchange())
				.with(UPDATE_ORDER_ROUTING_KEY);
	}
	@Bean
	public Binding cancel_order_binding(@Qualifier("cancel_order_queue") Queue queue, TopicExchange exchange) {
		return BindingBuilder
				.bind(cancel_order_queue())
				.to(exchange())
				.with(CANCEL_ORDER_ROUTING_KEY);
	}
	
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter());
		return template;
	}

}