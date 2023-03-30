package orderMyFood.RestaurantSearchService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import orderMyFood.RestaurantSearchService.model.Restaurant;

@Repository
public interface RestaurantRespository extends JpaRepository<Restaurant, Long>{

	@Query(value = "SELECT * FROM RESTAURANT_DETAILS WHERE LOWER (LOCATION) LIKE CONCAT('%',LOWER(:loc),'%')",nativeQuery = true)
	List<Restaurant> findByLocation(@Param("loc") String loc);
	

	@Query(value = "SELECT * FROM RESTAURANT_DETAILS WHERE "
			+ "(DISTANCE_FROM_CITY_CENTER - ?1 >=0 AND DISTANCE_FROM_CITY_CENTER - ?1 <=?2) OR "
			+ "(DISTANCE_FROM_CITY_CENTER - ?1 <0 AND ?1 - DISTANCE_FROM_CITY_CENTER <=?2)",nativeQuery = true)
	List<Restaurant> findByDistance(int distance, int radius);
	

	@Query(value = "SELECT * FROM RESTAURANT_DETAILS WHERE ID IN "
			+ "(SELECT RESTAURANT_FK FROM ITEM WHERE TYPE_OF_CUSINE = ?)",nativeQuery = true)
	List<Restaurant> findByCusine(String cusine);
	

	@Query(value = "SELECT * FROM RESTAURANT_DETAILS WHERE ID IN "
			+ "(SELECT RESTAURANT_FK FROM ITEM GROUP BY RESTAURANT_FK HAVING MIN(COST_OF_ITEM)<= ?)", nativeQuery = true)
	List<Restaurant> findByBudget(int amount);


	List<Restaurant> findByName(String name);

}
