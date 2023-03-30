package orderMyFood.RestaurantSearchService.Mappers;

import org.springframework.stereotype.Component;

import orderMyFood.RestaurantSearchService.Dto.RestaurantDto;
import orderMyFood.RestaurantSearchService.model.Restaurant;

@Component
public class RestaurantMapper {

	//DTO to Entity Converter
			public Restaurant dtoToRestaurant(RestaurantDto restaurantDto) {
				Restaurant restaurant = new Restaurant();
				restaurant.setId(restaurantDto.getId());
				restaurant.setName(restaurantDto.getName());
				restaurant.setLocation(restaurantDto.getLocation());
				restaurant.setDistance(restaurantDto.getDistance());
				restaurant.setItems(restaurantDto.getItems());
				return restaurant;
			}
			
			//Entity to DTO Converter
			public RestaurantDto restaurantToDto(Restaurant restaurant) {
				RestaurantDto restaurantDto = new RestaurantDto();
				restaurantDto.setId(restaurant.getId());
				restaurantDto.setName(restaurant.getName());
				restaurantDto.setLocation(restaurant.getLocation());
				restaurantDto.setDistance(restaurant.getDistance());
				restaurantDto.setItems(restaurant.getItems());
				return restaurantDto;
			}
}
