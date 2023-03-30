package orderMyFood.OrderManagementService.Mappers;



import org.springframework.stereotype.Component;

import orderMyFood.OrderManagementService.Model.Order;
import orderMyFood.OrderManagementService.Model.OrderItems;
import orderMyFood.OrderManagementService.Model.Dto.OrderDto;
import orderMyFood.OrderManagementService.Model.Dto.orderItemsDto;

@Component
public class OrderManagementMappers {


	//DTO to Entity Converter
	public Order dtoToOrder(OrderDto orderDto) {
		Order order = new Order();
		order.setId(orderDto.getId());
		order.setStatus(orderDto.getStatus());
		order.setOrderItems(orderDto.getOrderItems().stream().map(this::dtoToOrderItems).toList());
		order.setTotalPrice(orderDto.getTotalPrice());
		order.setCustomerName(orderDto.getCustomerName());
		order.setPhone(orderDto.getPhone());
		order.setAddress(orderDto.getAddress());
		return order;
	}
	
	public OrderItems dtoToOrderItems(orderItemsDto orderItemsDto) {
		OrderItems Items = new OrderItems();
		Items.setId(orderItemsDto.getId());
		Items.setItemCode(orderItemsDto.getItemCode());
		Items.setPrice(orderItemsDto.getPrice());
		Items.setQuantity(orderItemsDto.getQuantity());
		return Items;
	}
	
	//Entity to DTO Converter
	public OrderDto OrderToDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setStatus(order.getStatus());
		orderDto.setOrderItems(order.getOrderItems().stream().map(this::orderItemsToDto).toList());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setCustomerName(order.getCustomerName());
		orderDto.setPhone(order.getPhone());
		orderDto.setAddress(order.getAddress());
		return orderDto;
	}
	public orderItemsDto orderItemsToDto(OrderItems orderItems) {
		orderItemsDto ItemsDto = new orderItemsDto();
		ItemsDto.setId(orderItems.getId());
		ItemsDto.setItemCode(orderItems.getItemCode());
		ItemsDto.setPrice(orderItems.getPrice());
		ItemsDto.setQuantity(orderItems.getQuantity());
		return ItemsDto;
	}
	

}
