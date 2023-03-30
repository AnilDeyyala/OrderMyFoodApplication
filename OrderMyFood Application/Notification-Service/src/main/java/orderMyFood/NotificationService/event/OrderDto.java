package orderMyFood.NotificationService.event;


import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


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
	private Integer Phone;
	private String address;
	
	
}
