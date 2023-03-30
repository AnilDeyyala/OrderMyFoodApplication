package orderMyFood.OrderManagementService.Model.Dto;


import java.math.BigInteger;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import orderMyFood.OrderManagementService.Model.StatusEnum;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderDto {
	
	private Long id;
	
	private List<orderItemsDto> orderItems;
	
	private Integer totalPrice;
	
	private StatusEnum status;

	private String CustomerName;
	private String Phone;
	private String address;
	
	
}
