package orderMyFood.RestaurantSearchService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import orderMyFood.RestaurantSearchService.Dto.RestaurantDto;
import orderMyFood.RestaurantSearchService.Service.RestaurantServiceImpl;
import orderMyFood.RestaurantSearchService.model.Item;
import orderMyFood.RestaurantSearchService.model.cusineEnum;

@SpringBootApplication
@EnableEurekaClient
public class RestaurantSearchServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(RestaurantSearchServiceApplication.class, args);
	}


//	@Autowired
//	RestaurantServiceImpl restaurantServiceImpl;
//	
//	@Override
//	public void run(String... args) throws Exception {
//		
//		
//		RestaurantDto restaurant = new RestaurantDto();
//		List<Item> items1 = new ArrayList<>();
//		Item item1 = new Item();
//		item1.setItemName("Fish Fry");
//		item1.setPrice(250);
//		item1.setCusine(cusineEnum.INDIAN);
//		items1.add(item1);
//		
//		Item item2 = new Item();
//		item2.setItemName("Chicken Fry");
//		item2.setPrice(170);
//		item2.setCusine(cusineEnum.ITALIAN);
//		items1.add(item2);
//		
//		
//		restaurant.setName("Bhargav Mandi");
//		restaurant.setLocation("Benz circle, Vijayawada");
//		restaurant.setDistance(20);
//		restaurant.setItems(items1);
//		restaurantServiceImpl.saveRestaurant(restaurant);
//		
//		RestaurantDto restaurant1 = new RestaurantDto();
//		List<Item> items2 = new ArrayList<>();
//		Item item3 = new Item();
//		item3.setItemName("Prawn curry");
//		item3.setPrice(350);
//		item3.setCusine(cusineEnum.INDIAN);
//		items2.add(item3);
//		
//		Item item4 = new Item();
//		item4.setItemName("Fish Fry");
//		item4.setPrice(300);
//		item4.setCusine(cusineEnum.THAI);
//		items2.add(item4);
//		
//		
//		restaurant1.setName("Prameela Mandi");
//		restaurant1.setLocation("Necklace Road, Hyderabad");
//		restaurant1.setDistance(45);
//		restaurant1.setItems(items2);
//		restaurantServiceImpl.saveRestaurant(restaurant1);
//		
//	}

}
