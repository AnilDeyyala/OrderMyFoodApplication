package Project.OrderManagementService.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import orderMyFood.OrderManagementService.OrderManagementServiceApplication;
import orderMyFood.OrderManagementService.Model.Order;
import orderMyFood.OrderManagementService.Model.OrderItems;
import orderMyFood.OrderManagementService.Model.StatusEnum;
import orderMyFood.OrderManagementService.Repository.OrderRepository;

@DataJpaTest
@ContextConfiguration(classes = OrderManagementServiceApplication.class)
public class OrderRepositoryTests {
	
	@Autowired OrderRepository orderRepository;
	
	@Test
	public void testSaveOrderDetails() {
		Order order = loader();
		Order savedOrder = orderRepository.save(order);
		assertEquals(order.getCustomerName(), savedOrder.getCustomerName());
	}
	
	@Test
	public void testFindOrderById() {
		Order order = loader();
		Order savedOrder = orderRepository.save(order);
		Optional<Order> actualOrder = orderRepository.findById(savedOrder.getId());
		assertEquals(order.getCustomerName(), actualOrder.get().getCustomerName());
	}
	
	public static Order loader(){
		OrderItems orderItem1 = OrderItems.builder()
				.price(100)
				.quantity(2)
				.build();
		OrderItems orderItem2 = OrderItems.builder()
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
