package Project.RestaurantSearchService.Controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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

import orderMyFood.RestaurantSearchService.Controller.RestaurantController;
import orderMyFood.RestaurantSearchService.Dto.RestaurantDto;
import orderMyFood.RestaurantSearchService.Service.RestaurantServiceImpl;
import orderMyFood.RestaurantSearchService.model.Item;
import orderMyFood.RestaurantSearchService.model.cusineEnum;

@WebMvcTest(value = RestaurantController.class)
@AutoConfigureWebMvc
@ContextConfiguration(classes = RestaurantController.class)
@ExtendWith(MockitoExtension.class)
public class ControllerTests {
	
	@Autowired private MockMvc mockmvc;
	@MockBean RestaurantServiceImpl restaurantServiceImpl;
	@Autowired ObjectMapper objectMapper;
	
	
	@Test
//	@Disabled
	public void testCreateRestaurant() throws Exception {
		//Setup
		Item item = Item.builder()
				.itemName("Chicken Biriyani")
				.price(170)
				.cusine(cusineEnum.ITALIAN)
				.build();
		RestaurantDto restaurant1 = RestaurantDto.builder()
				.name("Hunger Birds")
				.location("LB nagar, Mylavaram")
				.distance(60)
				.items(List.of(item))
				.build();
		String inpInJson = objectMapper.writeValueAsString(restaurant1);
		Mockito.when(restaurantServiceImpl.saveRestaurant(Mockito.any(RestaurantDto.class))).thenReturn("Restaurant Details Saved Successfully");
		//Firing Request
		RequestBuilder req = MockMvcRequestBuilders.post("/api/v1/restaurant").accept(MediaType.APPLICATION_JSON)
				.content(inpInJson).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockmvc.perform(req).andReturn().getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("Restaurant Details Saved Successfully", response.getContentAsString());
		
	}
	
	@Test
//	@Disabled
	public void testRetriveAllRestaurants() throws Exception {
		//Setup
		List<RestaurantDto> restaurants = loader();
		Mockito.when(restaurantServiceImpl.getAllRestaurants()).thenReturn(restaurants);

		//Firing Request
		RequestBuilder req = MockMvcRequestBuilders.get("/api/v1/restaurant").accept(MediaType.APPLICATION_JSON);
		
		MockHttpServletResponse response = mockmvc.perform(req)
				.andExpect(status().isOk()).andReturn().getResponse();
		
		//Expected
		String inpInJson = objectMapper.writeValueAsString(restaurants);
		
		assertEquals(inpInJson, response.getContentAsString());
		
	}
	
	@Test
//	@Disabled
	public void testRetriveRestaurantByLocation() throws Exception {
		//Setup
		List<RestaurantDto> restaurants = loader();
		String Location = "vijayawada";
		
		//We need to pass the restaurant having location VIJAYAWADA. So we are passing the data at index 1 of restaurants
		Mockito.when(restaurantServiceImpl.getRestaurantByLocation(Location)).thenReturn(List.of(restaurants.get(1)));
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/restaurant/location/"+Location).accept(MediaType.APPLICATION_JSON);
		
		MockHttpServletResponse response = mockmvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
		
		assertEquals(objectMapper.writeValueAsString(List.of(restaurants.get(1))), response.getContentAsString());
		
	}
	
	@Test
//	@Disabled
	public void testRetriveRestuarantsByDistance() throws Exception {
		List<RestaurantDto> restaurant = List.of(loader().get(0));
		Integer distanceFromCityCenter = 18;   //Distance of the customer form city center
		Integer distanceRequired = 5;			//Restaurants inside the provided distances
		
		Mockito.when(restaurantServiceImpl.getRestaurantsByDistance(distanceFromCityCenter, distanceRequired))
						.thenReturn(restaurant);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/restaurant/distance/"+distanceFromCityCenter+"/"+
						distanceRequired).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockmvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
		
		assertEquals(objectMapper.writeValueAsString(restaurant), response.getContentAsString());
	}
	
	@Test
//	@Disabled
	public void testReriveRestaurantsByCusine() throws Exception {
		//Setup
		String cusine = "ITALIAN";
		List<RestaurantDto> restaurant = loader();
		
		Mockito.when(restaurantServiceImpl.getRestarantsByCusine(cusine)).thenReturn(restaurant);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/restaurant/cusine/"+cusine).accept(MediaType.APPLICATION_JSON);
		
		MockHttpServletResponse response = mockmvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
		
		assertEquals(objectMapper.writeValueAsString(restaurant), response.getContentAsString());
	}
	
	@Test
//	@Disabled
	public void testRetriveRestaurantsByBudget() throws Exception {
		//Setup
		List<RestaurantDto> restaurant = List.of(loader().get(0));
		Integer budget = 120;
		
		Mockito.when(restaurantServiceImpl.getRestaurantByBudget(budget)).thenReturn(restaurant);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/restaurant/budget/"+budget).accept(MediaType.APPLICATION_JSON);
		
		MockHttpServletResponse response = mockmvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
		
		assertEquals(objectMapper.writeValueAsString(restaurant), response.getContentAsString());
	}
	
	@Test
//	@Disabled
	public void testRetriveRestaurantByName() throws Exception {
		//Setup
		List<RestaurantDto> restaurant = List.of(loader().get(1));
		String name = "Bhargav Mandi";
		
		Mockito.when(restaurantServiceImpl.getRestaurantByName(name)).thenReturn(restaurant);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/restaurant/restaurant-name/"+name).accept(MediaType.APPLICATION_JSON);
		
		MockHttpServletResponse response = mockmvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
		
		assertEquals(objectMapper.writeValueAsString(restaurant), response.getContentAsString());
	}
	
	@Test
	@DisplayName("Retrive food menu based on id")
	public void testRetriveFoodMenuById() throws Exception{
		//Setup
		RestaurantDto restaurant = loader().get(0);
		restaurant.setId(1L);
		Long id = 1L;
		
		Mockito.when(restaurantServiceImpl.getFoodMenuById(id)).thenReturn(restaurant);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/restaurant/food-menu/"+id).accept(MediaType.APPLICATION_JSON);
		
		MockHttpServletResponse response = mockmvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
		
		assertEquals(objectMapper.writeValueAsString(restaurant), response.getContentAsString());
	}
	
	
	
	public static List<RestaurantDto> loader() {
		//Restaurant 1 details
		Item item = Item.builder()
				.itemName("Chicken Biriyani")
				.price(100)
				.cusine(cusineEnum.ITALIAN)
				.build();
		RestaurantDto restaurant1 = RestaurantDto.builder()
				.name("Hunger Birds")
				.location("LB nagar, Mylavaram")
				.distance(20)
				.items(List.of(item))
				.build();
		//Restaurant 2 details
		Item item1 = Item.builder()
				.itemName("Fish Fry")
				.price(250)
				.cusine(cusineEnum.INDIAN)
				.build();
		Item item2 = Item.builder()
				.itemName("Mushroom curry")
				.price(180)
				.cusine(cusineEnum.ITALIAN)
				.build();
		RestaurantDto restaurant2 = RestaurantDto.builder()
				.name("Bhargav Mandi")
				.location("Benz circle, Vijayawada")
				.distance(60)
				.items(List.of(item1, item2))
				.build();
		return List.of(restaurant1, restaurant2);
	}

}

