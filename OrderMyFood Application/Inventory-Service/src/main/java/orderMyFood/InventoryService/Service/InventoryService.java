package orderMyFood.InventoryService.Service;

import java.util.UUID;

import orderMyFood.InventoryService.Dto.InventoryDto;

public interface InventoryService {

	String addInventory(InventoryDto inventoryDto);

	InventoryDto getInventoryById(Long id);

	InventoryDto getInventoryByCode(UUID itemCode);

	Boolean isItemInStock(UUID itemCode, Long quantity);


}
