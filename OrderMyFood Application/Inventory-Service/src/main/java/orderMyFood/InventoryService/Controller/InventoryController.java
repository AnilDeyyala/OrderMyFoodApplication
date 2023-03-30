package orderMyFood.InventoryService.Controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import orderMyFood.InventoryService.Dto.InventoryDto;
import orderMyFood.InventoryService.Service.InventoryServiceImpl;

@RestController
@RequestMapping("/api/v1/inventory")
@AllArgsConstructor
public class InventoryController {
	
	private final InventoryServiceImpl inventoryServiceImpl;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public String addInventory(@RequestBody InventoryDto inventoryDto) {
		return inventoryServiceImpl.addInventory(inventoryDto);
	}
	
	@GetMapping("/id/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public InventoryDto getInventoryById(@PathVariable Long id) {
		return inventoryServiceImpl.getInventoryById(id);
	}
	
	@GetMapping("/item-code/{item-code}")
	@ResponseStatus(code = HttpStatus.OK)
	public InventoryDto getInventoryByCode(@PathVariable("item-code") UUID itemCode) {
		return inventoryServiceImpl.getInventoryByCode(itemCode);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/check-stock/{itemCode}/{quantity}")
	public Boolean checkStock(@PathVariable UUID itemCode, @PathVariable Long quantity) {
		return inventoryServiceImpl.isItemInStock(itemCode, quantity);
	}

}
