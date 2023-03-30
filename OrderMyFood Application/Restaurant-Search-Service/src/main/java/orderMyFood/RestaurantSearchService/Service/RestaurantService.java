package orderMyFood.RestaurantSearchService.Service;

import java.util.List;

import orderMyFood.RestaurantSearchService.Dto.RestaurantDto;

public interface RestaurantService {

	String saveRestaurant(RestaurantDto restaurantDto);

	List<RestaurantDto> getAllRestaurants();

	List<RestaurantDto> getRestaurantByLocation(String loc);

	List<RestaurantDto> getRestaurantsByDistance(int distance, int radius);

	List<RestaurantDto> getRestarantsByCusine(String cusine);

	List<RestaurantDto> getRestaurantByBudget(int amount);

	List<RestaurantDto> getRestaurantByName(String name);

	RestaurantDto getFoodMenuById(Long id);
}
