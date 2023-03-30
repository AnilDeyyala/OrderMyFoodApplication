package orderMyFood.InventoryService.Dto;

import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDto {
	
	private Long id;

	private UUID itemCode;
	
	private Long stock;
}
