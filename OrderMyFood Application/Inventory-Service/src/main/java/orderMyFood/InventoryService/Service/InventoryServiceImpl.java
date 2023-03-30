package orderMyFood.InventoryService.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import orderMyFood.InventoryService.Dto.InventoryDto;
import orderMyFood.InventoryService.Exception.ID_NOT_FOUND_EXCEPTION;
import orderMyFood.InventoryService.Exception.ITEM_CODE_NOT_FOUND_EXCEPTION;
import orderMyFood.InventoryService.Mapper.InventoryMapper;
import orderMyFood.InventoryService.Model.Inventory;
import orderMyFood.InventoryService.Repository.InventoryRepository;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

	private final InventoryRepository inventoryRepository;
	
	private final InventoryMapper inventoryMapper;
	
	@Override
	public String addInventory(InventoryDto inventoryDto) {
		inventoryRepository.save(inventoryMapper.DtoToInventory(inventoryDto));
		return "Inventory Added Successfully";
	}

	@Override
	public InventoryDto getInventoryById(Long id) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow(()->new ID_NOT_FOUND_EXCEPTION());
		return inventoryMapper.InventoryToDto(inventory);
	}
	
	@Override
	public InventoryDto getInventoryByCode(UUID itemCode) {
		Inventory inventory =  inventoryRepository.findByItemCode(itemCode).orElseThrow(()->new ITEM_CODE_NOT_FOUND_EXCEPTION());
		return inventoryMapper.InventoryToDto(inventory);
	}
	

	@Override
	public Boolean isItemInStock(UUID itemCode, Long quantity) {
		InventoryDto inventoryDto = getInventoryByCode(itemCode);
		Long actualQuantity = inventoryDto.getStock()-quantity;
		if(actualQuantity>=0) {
			inventoryDto.setStock(actualQuantity);
			addInventory(inventoryDto);
			return true;
		}
		return false;
	}


}
