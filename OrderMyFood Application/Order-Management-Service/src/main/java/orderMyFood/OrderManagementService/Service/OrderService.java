package orderMyFood.OrderManagementService.Service;

import java.util.List;

import orderMyFood.OrderManagementService.Exception.ITEM_OUT_OF_STOCK_EXCEPTION;
import orderMyFood.OrderManagementService.Model.Dto.OrderDto;
import orderMyFood.OrderManagementService.Model.Dto.orderItemsDto;

public interface OrderService {

	Integer getActualPrice(List<orderItemsDto> items);

	String placeOrder(OrderDto orderDto) throws ITEM_OUT_OF_STOCK_EXCEPTION;

	String updateOrder(OrderDto orderDto, Long id) throws Exception;

	OrderDto getOrderById(Long id) throws Exception;

	String cancelOrder(Long id);

}