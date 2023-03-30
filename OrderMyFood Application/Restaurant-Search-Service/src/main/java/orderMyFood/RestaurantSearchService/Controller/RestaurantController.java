package orderMyFood.RestaurantSearchService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import orderMyFood.RestaurantSearchService.Dto.RestaurantDto;
import orderMyFood.RestaurantSearchService.Service.RestaurantServiceImpl;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {
	
	@Autowired
	private RestaurantServiceImpl restaurantServiceImpl;
	
	//Saving new Restaurant details to the database
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public String createRestaurant(@RequestBody RestaurantDto restaurantDto) {
		return restaurantServiceImpl.saveRestaurant(restaurantDto);
	}
	
	//Retrieving all Restaurants details present in the database
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestaurantDto> retriveAllRestaurants(){
		return restaurantServiceImpl.getAllRestaurants();
	}
	
	//Retrieving Restaurants based on location irrespective of CASE (lower or upper or not complete location)
	@GetMapping("/location/{loc}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestaurantDto> retriveRestaurantByLocation(@PathVariable String loc) {
		return restaurantServiceImpl.getRestaurantByLocation(loc);
	}
	
	/*
	 *  Retrieving Restaurants based on Distances
		distance- Your distance form city center
		radius - Restaurants to be searched under the radius of 
	*/
	@GetMapping("/distance/{distance}/{radius}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestaurantDto> retriveRestuarantsByDistance(@PathVariable(name = "distance") int distance,
			@PathVariable(name="radius") int radius){
		return restaurantServiceImpl.getRestaurantsByDistance(distance, radius);
	}
	
	/*
	 * Retrieving Restaurants based on Type of cusine available
	 * */
	@GetMapping("/cusine/{cusine}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestaurantDto> reriveRestaurantsByCusine(@PathVariable String cusine){
		return restaurantServiceImpl.getRestarantsByCusine(cusine);
	}
	
	
	//Retrieving Restaurants based on budget
	@GetMapping("/budget/{amount}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestaurantDto> retriveRestaurantsByBudget(@PathVariable int amount){
		return restaurantServiceImpl.getRestaurantByBudget(amount);
	}
	
	
	//Retrieving Restaurants based on RestaurantName
	@GetMapping("/restaurant-name/{name}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestaurantDto> retriveRestaurantByName(@PathVariable String name){
		return restaurantServiceImpl.getRestaurantByName(name);
	}
	
	//Retrieving Food menu of the restaurant using id
	@GetMapping("/food-menu/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestaurantDto retriveFoodMenuById(@PathVariable Long id){
		return restaurantServiceImpl.getFoodMenuById(id);
	}

}
