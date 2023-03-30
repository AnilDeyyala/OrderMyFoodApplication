package orderMyFood.InventoryService.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import orderMyFood.InventoryService.Model.Inventory;
import orderMyFood.InventoryService.Repository.InventoryRepository;

@DataJpaTest
public class InventoryRepositoryTests {
	
	@Autowired InventoryRepository inventoryRepository;
	
	@Test
	public void testSaveInventory() {
		Inventory inventory = loader();
		Inventory savedInventory = inventoryRepository.save(inventory);
		assertEquals(inventory.getItemCode(), savedInventory.getItemCode());
	}
	
	@Test
	public void testInventoryById() {
		Inventory inventory = loader();
		inventoryRepository.save(inventory);
		Optional<Inventory> actualInventory = inventoryRepository.findById(2L);
		assertEquals(inventory.getStock(), actualInventory.get().getStock());
	}
	
	@Test
	public void testInventoryByItemCode() {
		Inventory inventory = loader();
		inventoryRepository.save(inventory);
		Optional<Inventory> actualInventory = inventoryRepository.findByItemCode(inventory.getItemCode());
		assertEquals(inventory.getStock(), actualInventory.get().getStock());
	}

	public static Inventory loader() {
		
		return Inventory.builder()
				.id(1L)
				.itemCode(UUID.randomUUID())
				.stock(10L)
				.build();
	}
	
}
