package orderMyFood.InventoryService.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import orderMyFood.InventoryService.Dto.InventoryDto;
import orderMyFood.InventoryService.Exception.ID_NOT_FOUND_EXCEPTION;
import orderMyFood.InventoryService.Exception.ITEM_CODE_NOT_FOUND_EXCEPTION;
import orderMyFood.InventoryService.Mapper.InventoryMapper;
import orderMyFood.InventoryService.Model.Inventory;
import orderMyFood.InventoryService.Repository.InventoryRepository;
import orderMyFood.InventoryService.Service.InventoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTests {
	
	@Mock InventoryRepository inventoryRepository;
	@InjectMocks InventoryServiceImpl inventoryServiceImpl;
	@Mock InventoryMapper inventoryMapper;
	
	@Test
	public void testAddInventory() {
		Inventory inventory = loader();
		Mockito.when(inventoryRepository.save(inventory)).thenReturn(null);
		Mockito.when(inventoryMapper.DtoToInventory(Mockito.any())).thenReturn(inventory);
		String actualInventory = inventoryServiceImpl.addInventory(dtoLoader());
		assertEquals("Inventory Added Successfully", actualInventory);
		verify(inventoryRepository,times(1)).save(inventory);
		
	}
	
	@Test
	public void testGetInventoryById() {
		Optional<Inventory> inventory = Optional.of(loader());
		Long id = inventory.get().getId();
		Mockito.when(inventoryRepository.findById(id)).thenReturn(inventory);
		Mockito.when(inventoryMapper.InventoryToDto(Mockito.any())).thenReturn(dtoLoader());
		InventoryDto actualInventory = inventoryServiceImpl.getInventoryById(id);
		assertEquals(dtoLoader().getStock(), actualInventory.getStock());
		verify(inventoryRepository).findById(id);
	}
	
	@Test
	public void testGetInventoryByIdException() {
		Mockito.when(inventoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(ID_NOT_FOUND_EXCEPTION.class, ()->inventoryServiceImpl.getInventoryById(1L));
	}
	
	@Test
	public void testGetInventoryByCode() {
		Optional<Inventory> inventory = Optional.of(loader());
		UUID uid = inventory.get().getItemCode();
		Mockito.when(inventoryRepository.findByItemCode(uid)).thenReturn(inventory);
		Mockito.when(inventoryMapper.InventoryToDto(Mockito.any())).thenReturn(dtoLoader());
		InventoryDto actualInventory = inventoryServiceImpl.getInventoryByCode(uid);
		assertEquals(dtoLoader().getStock(), actualInventory.getStock());
		verify(inventoryRepository).findByItemCode(uid);
	}
	@Test
	public void testGetInventoryByCodeException() {
		Mockito.when(inventoryRepository.findByItemCode(Mockito.any())).thenReturn(Optional.empty());
		assertThrows(ITEM_CODE_NOT_FOUND_EXCEPTION.class, ()->inventoryServiceImpl.getInventoryByCode(UUID.randomUUID()));
	}
	
	@Test
//	@Disabled
	public void testIsItemInStock() throws ITEM_CODE_NOT_FOUND_EXCEPTION{
		Optional<Inventory> inventory = Optional.of(loader());
		UUID uid = inventory.get().getItemCode();
		Mockito.when(inventoryRepository.findByItemCode(uid)).thenReturn(inventory);
		Mockito.when(inventoryMapper.InventoryToDto(Mockito.any())).thenReturn(dtoLoader());
		Boolean isInStock = inventoryServiceImpl.isItemInStock(uid, 2L);
		assertTrue(isInStock);
	}
	
	@Test
//	@Disabled
	public void testIsItemInStockFailed() throws ITEM_CODE_NOT_FOUND_EXCEPTION{
		Optional<Inventory> inventory = Optional.of(loader());
		UUID uid = inventory.get().getItemCode();
		Mockito.when(inventoryRepository.findByItemCode(uid)).thenReturn(inventory);
		Mockito.when(inventoryMapper.InventoryToDto(Mockito.any())).thenReturn(dtoLoader());
		Boolean isInStock = inventoryServiceImpl.isItemInStock(uid, 100L);
		assertFalse(isInStock);
	}
	public static InventoryDto dtoLoader() {
			
			return InventoryDto.builder()
					.id(1L)
					.itemCode(UUID.randomUUID())
					.stock(10L)
					.build();
	}
	public static Inventory loader() {
		
		return Inventory.builder()
				.id(1L)
				.itemCode(UUID.randomUUID())
				.stock(10L)
				.build();
}

}
