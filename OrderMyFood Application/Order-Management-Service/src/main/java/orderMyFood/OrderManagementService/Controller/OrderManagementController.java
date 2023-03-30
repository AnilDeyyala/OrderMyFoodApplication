package orderMyFood.OrderManagementService.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import orderMyFood.OrderManagementService.Exception.ITEM_OUT_OF_STOCK_EXCEPTION;
import orderMyFood.OrderManagementService.Model.Dto.OrderDto;
import orderMyFood.OrderManagementService.Model.Dto.orderItemsDto;
import orderMyFood.OrderManagementService.Service.OrderServiceImpl;

@RestController
@RequestMapping("/api/v1/order")
public class OrderManagementController {
	
	@Autowired OrderServiceImpl orderServiceImpl;

	
	//To get the actual price of the order
	@GetMapping("/price")
	public Integer getActualPrice(@RequestBody List<orderItemsDto> items) {
		return orderServiceImpl.getActualPrice(items);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@CircuitBreaker(name = "inventory", fallbackMethod = "fallback")
	public String placeOrder(@RequestBody OrderDto orderDto) throws ITEM_OUT_OF_STOCK_EXCEPTION {
		return orderServiceImpl.placeOrder(orderDto);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@CircuitBreaker(name = "inventory", fallbackMethod = "fallback")
	public String updateOrder(@RequestBody OrderDto orderDto, @PathVariable Long id) throws ITEM_OUT_OF_STOCK_EXCEPTION{
		return orderServiceImpl.updateOrder(orderDto,id);
	}
	
	//Fall Back method for circuit breaker
	public String fallback(Exception e) {
		return "Oops something went wrong please try again later!";
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public OrderDto getOrderById(@PathVariable Long id) {
		return orderServiceImpl.getOrderById(id);
	}
	
	@DeleteMapping("/{id}")
	public String cancelOrder(@PathVariable Long id) {
		return orderServiceImpl.cancelOrder(id);
		
	}
}
