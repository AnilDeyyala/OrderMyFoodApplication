package Project.OrderManagementService.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.client.RestTemplate;

import orderMyFood.OrderManagementService.Exception.ID_NOT_FOUND_EXCEPTION;
import orderMyFood.OrderManagementService.Exception.ITEM_OUT_OF_STOCK_EXCEPTION;
import orderMyFood.OrderManagementService.Mappers.OrderManagementMappers;
import orderMyFood.OrderManagementService.Model.Order;
import orderMyFood.OrderManagementService.Model.OrderItems;
import orderMyFood.OrderManagementService.Model.StatusEnum;
import orderMyFood.OrderManagementService.Model.Dto.OrderDto;
import orderMyFood.OrderManagementService.Model.Dto.orderItemsDto;
import orderMyFood.OrderManagementService.Repository.OrderRepository;
import orderMyFood.OrderManagementService.Service.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderManagementServiceTests {
	@Mock
	private OrderRepository orderRepository;
	@InjectMocks
	private OrderServiceImpl orderServiceImpl;
	@Mock private OrderManagementMappers orderManagementMappers;
	@Mock RestTemplate restTemplate;
	@Mock RabbitTemplate rabbitTemplate;

	
	@Test
	public void testGetActualPrice() {
		List<orderItemsDto> items = dtoLoader().getOrderItems();
		Integer actualAmount = 450;
		Integer amount = orderServiceImpl.getActualPrice(items);
		assertEquals(actualAmount, amount);
	}
	
	
	@Test
	public void tesGetOrderById() {
		Optional<Order> order = Optional.of(loader());
		Mockito.when(orderRepository.findById(1L)).thenReturn(order);
		Mockito.when(orderManagementMappers.OrderToDto(Mockito.any())).thenReturn(dtoLoader());
		OrderDto orderDto = orderServiceImpl.getOrderById(1L);
		assertEquals(dtoLoader().getAddress(), orderDto.getAddress());
	}
	
	@Test
	public void testGetOrderByIdException() {
		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(ID_NOT_FOUND_EXCEPTION.class, ()->orderServiceImpl.getOrderById(1L));
	}
	
	@Test
	public void testPlaceOrderException() throws ITEM_OUT_OF_STOCK_EXCEPTION {
		OrderDto orderDto = dtoLoader();
		Mockito.when(orderManagementMappers.dtoToOrder(orderDto)).thenReturn(loader());
		for(int j=0;j<2;j++)Mockito.when(orderManagementMappers.orderItemsToDto(Mockito.any())).thenReturn(orderDto.getOrderItems().get(j));
		for(int i=0;i<2;i++)Mockito.when(restTemplate.getForObject("http://inventory-service/api/v1/inventory/check-stock/"+orderDto.getOrderItems().get(i).getItemCode()+"/"+orderDto.getOrderItems().get(i).getQuantity()        
				,Boolean.class)).thenReturn(false);

		assertThrows(ITEM_OUT_OF_STOCK_EXCEPTION.class, ()->orderServiceImpl.placeOrder(orderDto));
	}
	@Test
	public void testUpdateOrderException() throws ITEM_OUT_OF_STOCK_EXCEPTION {
		OrderDto orderDto = dtoLoader();
		for(int j=0;j<2;j++)Mockito.when(orderManagementMappers.dtoToOrderItems(Mockito.any())).thenReturn(loader().getOrderItems().get(j));
		for(int i=0;i<2;i++)Mockito.when(restTemplate.getForObject("http://inventory-service/api/v1/inventory/check-stock/"+orderDto.getOrderItems().get(i).getItemCode()+"/"+orderDto.getOrderItems().get(i).getQuantity()        
				,Boolean.class)).thenReturn(false);
		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(loader()));

		assertThrows(ITEM_OUT_OF_STOCK_EXCEPTION.class, ()->orderServiceImpl.updateOrder(orderDto, orderDto.getId()));
	}
	
	@Test
	public void testCancelOrderException() {
		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(ID_NOT_FOUND_EXCEPTION.class, ()->orderServiceImpl.cancelOrder(Mockito.anyLong()));

	}
	
	public static OrderDto dtoLoader(){
		orderItemsDto orderItem1 = orderItemsDto.builder()
				.price(100)
				.itemCode(UUID.fromString("F92e1764-Fb9e-4d5c-9500-A8b7c993b32d"))
				.quantity(2)
				.build();
		orderItemsDto orderItem2 = orderItemsDto.builder()
				.itemCode(UUID.fromString("8c47430b-2754-496e-A0a8-79b5bcb39ccc"))
				.price(250)
				.quantity(1)
				.build();
		return OrderDto.builder()
				.id(1L)
				.CustomerName("Bhargav")
				.Phone("6303778541")
				.address("6-375 RTC colony Jaggayyapet 521175")
				.orderItems(List.of(orderItem1, orderItem2))
				.status(StatusEnum.PLACED)
				.totalPrice(450)
				.build();
	}
	public static Order loader(){
		OrderItems orderItem1 = OrderItems.builder()
				.itemCode(UUID.fromString("F92e1764-Fb9e-4d5c-9500-A8b7c993b32d"))
				.price(100)
				.quantity(2)
				.build();
		OrderItems orderItem2 = OrderItems.builder()
				.itemCode(UUID.fromString("8c47430b-2754-496e-A0a8-79b5bcb39ccc"))
				.price(250)
				.quantity(1)
				.build();
		return Order.builder()
				.id(1L)
				.customerName("Bhargav")
				.phone("6303778541")
				.address("6-375 RTC colony Jaggayyapet 521175")
				.orderItems(List.of(orderItem1, orderItem2))
				.status(StatusEnum.PLACED)
				.totalPrice(450)
				.build();
	}
}
