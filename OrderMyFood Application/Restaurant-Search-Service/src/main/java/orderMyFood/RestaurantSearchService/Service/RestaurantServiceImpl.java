package orderMyFood.RestaurantSearchService.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import orderMyFood.RestaurantSearchService.Exception.ID_NOT_FOUND_EXCEPTION;
import orderMyFood.RestaurantSearchService.Mappers.RestaurantMapper;
import orderMyFood.RestaurantSearchService.Dto.RestaurantDto;
import orderMyFood.RestaurantSearchService.Repository.RestaurantRespository;
import orderMyFood.RestaurantSearchService.model.Restaurant;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService{

		@Autowired
		private RestaurantRespository restaurantRespository;
		@Autowired
		private RestaurantMapper restaurantMapper;
		
		@Override
		public String saveRestaurant(RestaurantDto restaurantDto) {
			restaurantRespository.save(restaurantMapper.dtoToRestaurant(restaurantDto));
			return "Restaurant Details Saved Successfully";
		}
	
		@Override
		public List<RestaurantDto> getAllRestaurants() {
			List<Restaurant> restaurants =  restaurantRespository.findAll();
			return restaurants.stream().map(restaurant->restaurantMapper.restaurantToDto(restaurant)).toList();
			
		}
		
	
		@Override
		public List<RestaurantDto> getRestaurantByLocation(String loc) {
			List<Restaurant> restaurants = restaurantRespository.findByLocation(loc);
			return restaurants.stream().map(restaurant->restaurantMapper.restaurantToDto(restaurant)).toList();
		}


		@Override
		public List<RestaurantDto> getRestaurantsByDistance(int distance, int radius) {
			List<Restaurant> restaurants = restaurantRespository.findByDistance(distance, radius);
			System.out.println(restaurants);
			return restaurants.stream().map(restaurant->restaurantMapper.restaurantToDto(restaurant)).toList();
		}

		@Override
		public List<RestaurantDto> getRestarantsByCusine(String cusine) {
			List<Restaurant> restaurants = restaurantRespository.findByCusine(cusine);
			System.out.println(restaurants);
			return restaurants.stream().map(restaurant->restaurantMapper.restaurantToDto(restaurant)).toList();	
		}

		@Override
		public List<RestaurantDto> getRestaurantByBudget(int amount) {
			List<Restaurant> restaurants = restaurantRespository.findByBudget(amount);
			System.out.println(restaurants);
			return restaurants.stream().map(restaurant->restaurantMapper.restaurantToDto(restaurant)).toList();	
		}

		@Override
		public List<RestaurantDto> getRestaurantByName(String name) {
			List<Restaurant> restaurants = restaurantRespository.findByName(name);
			System.out.println(restaurants);
			return restaurants.stream().map(restaurant->restaurantMapper.restaurantToDto(restaurant)).toList();
		}

		@Override
		public RestaurantDto getFoodMenuById(Long id) {
			Restaurant restaurant = restaurantRespository.findById(id).orElseThrow(()->new ID_NOT_FOUND_EXCEPTION());
			return restaurantMapper.restaurantToDto(restaurant);
		}
		
		



	

}
