package orderMyFood.InventoryService.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import orderMyFood.InventoryService.Controller.InventoryController;
import orderMyFood.InventoryService.Dto.InventoryDto;
import orderMyFood.InventoryService.Service.InventoryServiceImpl;

@WebMvcTest(value = InventoryController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = InventoryController.class)
@AutoConfigureWebMvc
public class InventoryControllerTests {

	@Autowired ObjectMapper objectMapper;
	@MockBean private InventoryServiceImpl inventoryServiceImpl;
	@Autowired private MockMvc mockMvc;
	
	@Test
	public void testAddInventory() throws Exception {
		InventoryDto inventoryDto = loader();
		Mockito.when(inventoryServiceImpl.addInventory(inventoryDto)).thenReturn("Inventory Added Successfully");
		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/inventory").accept(MediaType.ALL_VALUE)
				.content(objectMapper.writeValueAsString(inventoryDto)).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("Inventory Added Successfully", response.getContentAsString());
	}
	@Test
	public void testGetInventoryById() throws Exception {
		InventoryDto inventoryDto = loader();
		Long id = 1L;
		Mockito.when(inventoryServiceImpl.getInventoryById(id)).thenReturn(inventoryDto);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/inventory/id/"+id).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(objectMapper.writeValueAsString(inventoryDto), response.getContentAsString());
	}
	
	@Test
	public void testGetInventoryByCode() throws Exception {
		InventoryDto inventoryDto = loader();
		UUID uid = inventoryDto.getItemCode();
		Mockito.when(inventoryServiceImpl.getInventoryByCode(uid)).thenReturn(inventoryDto);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/inventory/item-code/"+uid).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(objectMapper.writeValueAsString(inventoryDto), response.getContentAsString());
	}
	
	@Test
	public void testCheckStock() throws Exception {
		UUID uid = UUID.randomUUID();
		Mockito.when(inventoryServiceImpl.isItemInStock(uid,2L)).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/inventory/check-stock/"+uid+"/"+2).accept(MediaType.ALL_VALUE);
		MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertTrue(response.getContentAsString().equals("true"));
	}
	
	public static InventoryDto loader() {
		
		return InventoryDto.builder()
				.id(1L)
				.itemCode(UUID.randomUUID())
				.stock(10L)
				.build();
	}
}
