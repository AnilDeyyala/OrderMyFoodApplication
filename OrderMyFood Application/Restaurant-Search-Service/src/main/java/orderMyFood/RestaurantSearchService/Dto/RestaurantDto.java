package orderMyFood.RestaurantSearchService.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import orderMyFood.RestaurantSearchService.model.Item;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class RestaurantDto {
	
	private Long id;

	private String name;
	
	private String location;
	
	private int distance;
	
	private List<Item> items;
	
	@Override
	public String toString() {
		return "RestaurantDto [name=" + name + ", location=" + location + ", distance=" + distance + ", items=" + items
				+ "]";
	}
	
	
}
