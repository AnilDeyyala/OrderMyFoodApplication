package Project.OrderManagementService.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import orderMyFood.OrderManagementService.Controller.OrderManagementController;

import orderMyFood.OrderManagementService.Model.StatusEnum;
import orderMyFood.OrderManagementService.Model.Dto.OrderDto;
import orderMyFood.OrderManagementService.Model.Dto.orderItemsDto;
import orderMyFood.OrderManagementService.Service.OrderServiceImpl;

@ExtendWith(value = MockitoExtension.class)
@AutoConfigureWebMvc
@ContextConfiguration(classes = OrderManagementController.class)
@WebMvcTest(value = OrderManagementController.class)
public class OrderManagementControllerTests {
//	@Autowired
//	private OrderManagementMappers orderManagementMappers;
	@MockBean private OrderServiceImpl orderServiceImpl;
	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;
	
	@Test
	public void testGetActualPrice() throws Exception {
		orderItemsDto orderItem1 = orderItemsDto.builder()
				.price(100)
				.quantity(2)
				.build();
		orderItemsDto orderItem2 = orderItemsDto.builder()
				.price(250)
				.quantity(1)
				.build();
		List<orderItemsDto> orderItems = List.of(orderItem1, orderItem2);
		String inpInJson = objectMapper.writeValueAsString(orderItems);
		Integer actualPrice = 450; //Expected Output
		Mockito.when(orderServiceImpl.getActualPrice(orderItems)).thenReturn(actualPrice);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/order/price").accept(MediaType.ALL_VALUE)
				.content(inpInJson).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		
		assertEquals(actualPrice.toString(), response.getContentAsString());
	}
	
	@Test
	public void testPlaceOrder() throws Exception {
		OrderDto orderDto = loader();
		
		Mockito.when(orderServiceImpl.placeOrder(Mockito.any(OrderDto.class))).thenReturn("Order Saved successfully");
		
		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/order").accept(MediaType.ALL)
				.content(objectMapper.writeValueAsString(orderDto)).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("Order Saved successfully", response.getContentAsString());
	
	}
	
	@Test
	public void testUpdateOrder() throws Exception {
		OrderDto orderDto = loader();
		Mockito.when(orderServiceImpl.updateOrder(orderDto,1L)).thenReturn("Order Updated successfully");
		RequestBuilder request = MockMvcRequestBuilders.put("/api/v1/order/1").accept(MediaType.ALL_VALUE)
				.content(objectMapper.writeValueAsString(orderDto)).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("Order Updated successfully", response.getContentAsString());
	}
	
	@Test
	public void testGetOrderById() throws Exception {
		OrderDto orderDto = loader();
		Mockito.when(orderServiceImpl.getOrderById(1L)).thenReturn(orderDto);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/order/1").accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(objectMapper.writeValueAsString(orderDto), response.getContentAsString());
	}
	
	@Test
	public void testCancelOrder() throws Exception {
		Long id = 1L;
		Mockito.when(orderServiceImpl.cancelOrder(id)).thenReturn("Order with id: "+id+" Canceled successfully");
		RequestBuilder request  = MockMvcRequestBuilders.delete("/api/v1/order/"+id).accept(MediaType.ALL_VALUE);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("Order with id: "+id+" Canceled successfully", response.getContentAsString());
	}
	
	
	public static OrderDto loader(){
		orderItemsDto orderItem1 = orderItemsDto.builder()
				.price(100)
				.quantity(2)
				.build();
		orderItemsDto orderItem2 = orderItemsDto.builder()
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

}
