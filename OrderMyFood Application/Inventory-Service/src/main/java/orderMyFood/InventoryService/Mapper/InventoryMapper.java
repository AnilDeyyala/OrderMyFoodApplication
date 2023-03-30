package orderMyFood.InventoryService.Mapper;

import org.springframework.stereotype.Component;

import orderMyFood.InventoryService.Dto.InventoryDto;
import orderMyFood.InventoryService.Model.Inventory;

@Component
public class InventoryMapper {

	//DtoToEntity
	public Inventory DtoToInventory(InventoryDto inventoryDto) {
		
		return Inventory.builder()
				.id(inventoryDto.getId())
				.itemCode(inventoryDto.getItemCode())
				.stock(inventoryDto.getStock())
				.build();
		
	}
	
	//EntityToDto
	public InventoryDto InventoryToDto(Inventory inventory) {
		return InventoryDto.builder()
				.id(inventory.getId())
				.itemCode(inventory.getItemCode())
				.stock(inventory.getStock())
				.build(); 
	}
}
