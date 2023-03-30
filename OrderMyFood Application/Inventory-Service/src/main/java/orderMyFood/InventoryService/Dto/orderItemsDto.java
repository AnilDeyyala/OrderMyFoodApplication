package orderMyFood.InventoryService.Dto;


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
	
	private UUID item_code;
	
	private Integer price;
	
	private Integer quantity;
}
