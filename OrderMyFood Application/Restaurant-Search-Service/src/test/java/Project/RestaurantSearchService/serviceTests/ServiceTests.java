package Project.RestaurantSearchService.serviceTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import orderMyFood.RestaurantSearchService.Dto.RestaurantDto;
import orderMyFood.RestaurantSearchService.Exception.ID_NOT_FOUND_EXCEPTION;
import orderMyFood.RestaurantSearchService.Mappers.RestaurantMapper;
import orderMyFood.RestaurantSearchService.Repository.RestaurantRespository;
import orderMyFood.RestaurantSearchService.Service.RestaurantServiceImpl;
import orderMyFood.RestaurantSearchService.model.Item;
import orderMyFood.RestaurantSearchService.model.Restaurant;
import orderMyFood.RestaurantSearchService.model.cusineEnum;




@ExtendWith(MockitoExtension.class)
public class ServiceTests {
	
	@Mock
	private RestaurantRespository restaurantRespository;
	
	@InjectMocks
	private RestaurantServiceImpl restaurantServiceImpl;
	
	@Mock
	private RestaurantMapper restaurantMapper;
	
	@Test
//	@Disabled
	public void saveRestuarantTest() {
		RestaurantDto restaurant = DtoLoader().get(0);
		System.out.println(restaurant+" fasa");
		Mockito.when(restaurantRespository.save(Mockito.any())).thenReturn(null);
		String res = restaurantServiceImpl.saveRestaurant(restaurant);
		assertEquals("Restaurant Details Saved Successfully", res);
		verify(restaurantRespository).save(restaurantMapper.dtoToRestaurant(restaurant));
	}
	
	@Test
//	@Disabled
	public void testGetAllRestaurants(){
		List<Restaurant> restaurants = loader();
		Mockito.when(restaurantRespository.findAll()).thenReturn(restaurants);
		Mockito.when(restaurantMapper.restaurantToDto(restaurants.get(0))).thenReturn(DtoLoader().get(0));
		Mockito.when(restaurantMapper.restaurantToDto(restaurants.get(1))).thenReturn(DtoLoader().get(1));
		List<RestaurantDto> actualRestaurant = restaurantServiceImpl.getAllRestaurants();
		System.out.println("actual: "+actualRestaurant);
		assertNotNull(actualRestaurant);
		assertEquals(DtoLoader().get(0).getName(), actualRestaurant.get(0).getName());
		verify(restaurantRespository).findAll();

	}
	
	@Test
//	@Disabled
	public void testGetRestaurantByLocation() {
		Restaurant restaurant = loader().get(1);
		String Location = "vijayawada";
		Mockito.when(restaurantRespository.findByLocation(Location)).thenReturn(List.of(restaurant));
		Mockito.when(restaurantMapper.restaurantToDto(restaurant)).thenReturn(DtoLoader().get(1));
		List<RestaurantDto> restaurantDtos = restaurantServiceImpl.getRestaurantByLocation(Location);
		
		assertNotNull(restaurantDtos);
		assertEquals(DtoLoader().get(1).getName(), restaurantDtos.get(0).getName());
		verify(restaurantRespository).findByLocation(Location);

	}
	
	@Test
//	@Disabled
	public void testGetRestaurantsByDistance() {
		Restaurant restaurant = loader().get(0);
		int distance = 18;
		int radius = 5;
		Mockito.when(restaurantRespository.findByDistance(distance, radius)).thenReturn(List.of(restaurant));
		Mockito.when(restaurantMapper.restaurantToDto(restaurant)).thenReturn(DtoLoader().get(0));
		List<RestaurantDto> restaurantDtos = restaurantServiceImpl.getRestaurantsByDistance(distance, radius);
		
		assertNotNull(restaurantDtos);
		assertEquals(DtoLoader().get(0).getName(), restaurantDtos.get(0).getName());
		verify(restaurantRespository).findByDistance(distance, radius);
	}
	
	@Test
//	@Disabled
	public void testGetRestarantsByCusine() {
		Restaurant restaurant = loader().get(1);
		Mockito.when(restaurantRespository.findByCusine("INDIAN")).thenReturn(List.of(restaurant));
		Mockito.when(restaurantMapper.restaurantToDto(restaurant)).thenReturn(DtoLoader().get(1));
		List<RestaurantDto> restaurantDtos = restaurantServiceImpl.getRestarantsByCusine("INDIAN");
		
		assertNotNull(restaurantDtos);
		assertEquals(DtoLoader().get(1).getName(), restaurantDtos.get(0).getName());
		verify(restaurantRespository).findByCusine("INDIAN");
	}
	
	@Test
//	@Disabled
	public void testGetRestaurantByBudget() {
		Restaurant restaurant = loader().get(0);
		Mockito.when(restaurantRespository.findByBudget(120)).thenReturn(List.of(restaurant));
		Mockito.when(restaurantMapper.restaurantToDto(restaurant)).thenReturn(DtoLoader().get(0));
		List<RestaurantDto> restaurantDtos = restaurantServiceImpl.getRestaurantByBudget(120);
		
		assertNotNull(restaurantDtos);
		assertEquals(DtoLoader().get(0).getName(), restaurantDtos.get(0).getName());
		verify(restaurantRespository).findByBudget(120);
	}
	
	@Test
//	@Disabled
	public void testGetRestaurantByName() {
		Restaurant restaurant = loader().get(0);
		String name = "Hunger Birds";
		Mockito.when(restaurantRespository.findByName(name)).thenReturn(List.of(restaurant));
		Mockito.when(restaurantMapper.restaurantToDto(restaurant)).thenReturn(DtoLoader().get(0));
		List<RestaurantDto> restaurantDtos = restaurantServiceImpl.getRestaurantByName(name);
		
		assertNotNull(restaurantDtos);
		assertEquals(DtoLoader().get(0).getName(), restaurantDtos.get(0).getName());
		verify(restaurantRespository).findByName(name);
	}
	
	@Test
	public void testGetFoodMenuById() {
		Optional<Restaurant> restaurant = Optional.of(loader().get(1));
		Long id = 2L;
		Mockito.when(restaurantRespository.findById(id)).thenReturn(restaurant);
		Mockito.when(restaurantMapper.restaurantToDto(Mockito.any())).thenReturn((DtoLoader().get(1)));
		RestaurantDto restaurantDtos = restaurantServiceImpl.getFoodMenuById(id);
		
		assertNotNull(restaurantDtos);
		assertEquals(DtoLoader().get(1).getName(), restaurantDtos.getName());
		verify(restaurantRespository).findById(id);
	}
	
	@Test
	public void testGetFoodMenuByIdException() {
		Long id = 2L;
		Mockito.when(restaurantRespository.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(ID_NOT_FOUND_EXCEPTION.class, ()->restaurantServiceImpl.getFoodMenuById(id));
		verify(restaurantRespository).findById(id);
	}
	
	public static List<RestaurantDto> DtoLoader() {
		//Restaurant 1 details
		Item item = Item.builder()
				.itemName("Chicken Biriyani")
				.price(100)
				.cusine(cusineEnum.ITALIAN)
				.build();
		RestaurantDto restaurant1 = RestaurantDto.builder()
				.id(1L)
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
				.id(2L)
				.name("Bhargav Mandi")
				.location("Benz circle, Vijayawada")
				.distance(60)
				.items(List.of(item1, item2))
				.build();
		return List.of(restaurant1, restaurant2);
	}
	
	public static List<Restaurant> loader() {
		//Restaurant 1 details
		Item item = Item.builder()
				.itemName("Chicken Biriyani")
				.price(100)
				.cusine(cusineEnum.ITALIAN)
				.build();
		Restaurant restaurant1 = Restaurant.builder()
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
		Restaurant restaurant2 = Restaurant.builder()
				.name("Bhargav Mandi")
				.location("Benz circle, Vijayawada")
				.distance(60)
				.items(List.of(item1, item2))
				.build();
		return List.of(restaurant1, restaurant2);
	}
	


}
