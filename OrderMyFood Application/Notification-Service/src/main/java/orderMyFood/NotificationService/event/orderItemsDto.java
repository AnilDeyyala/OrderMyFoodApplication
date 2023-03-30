package orderMyFood.NotificationService.event;


import java.util.UUID;

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
public class orderItemsDto {
	
	
	private Long id;
	
	private UUID itemCode;
	
	private Integer price;
	
	private Integer quantity;
}
