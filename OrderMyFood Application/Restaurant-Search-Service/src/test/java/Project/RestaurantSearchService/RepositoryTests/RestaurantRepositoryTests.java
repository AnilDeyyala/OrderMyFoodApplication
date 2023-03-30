package Project.RestaurantSearchService.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import orderMyFood.RestaurantSearchService.RestaurantSearchServiceApplication;
import orderMyFood.RestaurantSearchService.Repository.RestaurantRespository;
import orderMyFood.RestaurantSearchService.model.Item;
import orderMyFood.RestaurantSearchService.model.Restaurant;
import orderMyFood.RestaurantSearchService.model.cusineEnum;

@DataJpaTest
@ContextConfiguration(classes = RestaurantSearchServiceApplication.class)
@ExtendWith(SpringExtension.class)
public class RestaurantRepositoryTests {

	@Autowired RestaurantRespository restaurantRespository;
	@AfterEach
	void tearDown() {
		restaurantRespository.deleteAll();
		restaurantRespository.flush();
	}
	
	@Test
	public void testGetFoodMenuById() {
		List<Restaurant> restaurant = loader();
		Restaurant savedRestaurant = restaurantRespository.save(restaurant.get(0));
		restaurantRespository.save(restaurant.get(1));
		Optional<Restaurant> actualRestaurants = restaurantRespository.findById(savedRestaurant.getId());
		assertEquals(savedRestaurant.getName(), actualRestaurants.get().getName());
	}
	
	@Test
	public void testSaveRestaurant() {
		Restaurant restaurant = loader().get(0);
		Restaurant saved_restaurant = restaurantRespository.save(restaurant);
		assertEquals(restaurant.getName(), saved_restaurant.getName());
	}
	
	@Test
	public void testFindAllRestaurants() {
		List<Restaurant> restaurants = loader();
		restaurantRespository.saveAll(restaurants);
		List<Restaurant> actual_restaurants = restaurantRespository.findAll();
		assertEquals(2, actual_restaurants.size());
	}
	
	@Test
	public void testFindRestaurantByLocation() {
		List<Restaurant> restaurant = loader();
		restaurantRespository.saveAll(restaurant);
		List<Restaurant> actualRestaurants = restaurantRespository.findByLocation("Mylavaram");
		assertEquals(restaurant.get(0).getName(), actualRestaurants.get(0).getName());
	}
	@Test
	public void testFindByDistance() {
		List<Restaurant> restaurant = loader();
		restaurantRespository.saveAll(restaurant);
		List<Restaurant> actualRestaurants = restaurantRespository.findByLocation("Mylavaram");
		assertEquals(restaurant.get(0).getName(), actualRestaurants.get(0).getName());
	}
	
	@Test
	public void testFindByCusine() {
		List<Restaurant> restaurant = loader();
		restaurantRespository.saveAll(restaurant);
		List<Restaurant> actualRestaurants = restaurantRespository.findByCusine("INDIAN");
		assertEquals(restaurant.get(1).getName(), actualRestaurants.get(0).getName());
		
	}
	@Test
	public void testFindByBudget() {
		List<Restaurant> restaurant = loader();
		restaurantRespository.saveAll(restaurant);
		List<Restaurant> actualRestaurants = restaurantRespository.findByBudget(120);
		assertEquals(restaurant.get(0).getName(), actualRestaurants.get(0).getName());
	}
	
	@Test
	public void testFindByName() {
		List<Restaurant> restaurant = loader();
		restaurantRespository.saveAll(restaurant);
		List<Restaurant> actualRestaurants = restaurantRespository.findByName("Hunger Birds");
		assertEquals(restaurant.get(0).getName(), actualRestaurants.get(0).getName());
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
