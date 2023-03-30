package orderMyFood.OrderManagementService.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orderMyFood.OrderManagementService.Config.RabbitMqConfig;
import orderMyFood.OrderManagementService.Exception.ID_NOT_FOUND_EXCEPTION;
import orderMyFood.OrderManagementService.Exception.ITEM_OUT_OF_STOCK_EXCEPTION;
import orderMyFood.OrderManagementService.Mappers.OrderManagementMappers;
import orderMyFood.OrderManagementService.Model.Order;
import orderMyFood.OrderManagementService.Model.StatusEnum;
import orderMyFood.OrderManagementService.Model.Dto.OrderDto;
import orderMyFood.OrderManagementService.Model.Dto.orderItemsDto;
import orderMyFood.OrderManagementService.Repository.OrderRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
		
	private final OrderRepository orderRespository;
	
	private final RestTemplate restTemplate;
		
	private final OrderManagementMappers orderManagementMappers;
	
	private final RabbitTemplate rabbitTemplate;

	@Override
	public Integer getActualPrice(List<orderItemsDto> items) {
		Integer totalPrice = 0;
		for(orderItemsDto item : items) {
			totalPrice+=(item.getPrice()*item.getQuantity());
		}
		return totalPrice;
	}
	
	@Override
	public String placeOrder(OrderDto orderDto) throws ITEM_OUT_OF_STOCK_EXCEPTION {
		Order order = orderManagementMappers.dtoToOrder(orderDto);
		System.out.println(orderDto);
		order.setStatus(StatusEnum.PLACED);
		order.setTotalPrice(this.getActualPrice(order.getOrderItems().stream().map(item->orderManagementMappers.orderItemsToDto(item)).toList()));
		
		//checking the inventory stocks before placing the order
		List<orderItemsDto> orderItems = orderDto.getOrderItems();
		
		if(checkItemStock(orderItems)) {
			log.info("Order Saved successfully");
			orderRespository.save(order);
			
//			Sending Notification to the delivery team
			rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.PLACE_ORDER_ROUTING_KEY, orderDto);

			return "Order Saved successfully";

		}
		else
			throw new ITEM_OUT_OF_STOCK_EXCEPTION();
	}

	@Override
	public String updateOrder(OrderDto orderDto, Long id) throws ITEM_OUT_OF_STOCK_EXCEPTION{
		Order order = orderRespository.findById(id).orElseThrow(()->new ID_NOT_FOUND_EXCEPTION());
		Order updatedOrder = new Order();
		updatedOrder.setId(id);
		updatedOrder.setOrderPlacedOn(order.getOrderPlacedOn());
		updatedOrder.setOrderItems(orderDto.getOrderItems().stream().map(item->orderManagementMappers.dtoToOrderItems(item)).toList());
		updatedOrder.setTotalPrice(this.getActualPrice(orderDto.getOrderItems()));
		updatedOrder.setStatus(StatusEnum.PLACED);
		updatedOrder.setAddress(orderDto.getAddress());
		updatedOrder.setPhone(orderDto.getPhone());
		updatedOrder.setCustomerName(orderDto.getCustomerName());
		if(checkItemStock(orderDto.getOrderItems())) {
			orderRespository.save(updatedOrder);
			
//			Sending Notification to the delivery team
			rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.UPDATE_ORDER_ROUTING_KEY, orderDto);
			
			return "Order Updated successfully";
		}
		else
			throw new ITEM_OUT_OF_STOCK_EXCEPTION();

	}
	
	//To check inventory stocks using REST TEMPLATE
	private Boolean checkItemStock(List<orderItemsDto> orderItems) {
		Boolean flag = true;
		for(orderItemsDto item : orderItems) {
			Boolean isInStock = restTemplate.getForObject("http://inventory-service/api/v1/inventory/check-stock/"+item.getItemCode()+"/"+item.getQuantity(),
					Boolean.class);
			if(!isInStock) flag=false;
		}
		return flag;
	}
	
	

	@Override
	public OrderDto getOrderById(Long id) {
		Order order =  orderRespository.findById(id).orElseThrow(()->new ID_NOT_FOUND_EXCEPTION());
		return orderManagementMappers.OrderToDto(order);
	}

	@Override
	public String cancelOrder(Long id) {

		Order order =  orderRespository.findById(id).orElseThrow(()->new ID_NOT_FOUND_EXCEPTION());
		order.setStatus(StatusEnum.CANCELED);
		
		//send communication to delivery team.
		rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.CANCEL_ORDER_ROUTING_KEY,
				orderManagementMappers.OrderToDto(order));
		return "Order with id: "+id+" Canceled successfully";

	}
	
	

	

	
}
