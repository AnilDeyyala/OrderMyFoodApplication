package orderMyFood.NotificationService.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import orderMyFood.NotificationService.Config.RabbitMqConfig;
import orderMyFood.NotificationService.event.OrderDto;

@Component
public class Consumer {

	@RabbitListener(queues = RabbitMqConfig.PLACE_ORDER_QUEUE)
	public void placeOrderStatusConsumer(OrderDto orderDto) {
		System.out.println("Order Placed! Order details provided below");
		System.out.println(orderDto);
	}
	
	@RabbitListener(queues = RabbitMqConfig.UPDATE_ORDER_QUEUE)
	public void updateOrderStatusConsumer(OrderDto orderDto) {
		System.out.println("Order Updated! Delivery details provided below");
		System.out.println(orderDto);
	}
	
	@RabbitListener(queues = RabbitMqConfig.CANCEL_ORDER_QUEUE)
	public void cancelOrderStatusConsumer(OrderDto orderDto) {
		System.out.println("Order Canceled! Order details provided below");
		System.out.println(orderDto);
	}
}
