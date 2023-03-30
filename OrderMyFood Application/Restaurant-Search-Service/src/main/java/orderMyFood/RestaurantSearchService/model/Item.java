package orderMyFood.RestaurantSearchService.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Entity
@Table(name="Item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Item {

	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @Column(name = "item_code", length = 16,unique = true)
	@Builder.Default
	private UUID itemCode = UUID.randomUUID();
	
	@Column(name = "Item_name")
	private String itemName;
	
	@Column(name = "cost_of_item")
	private int price;
	
	@Column(name = "Type_of_cusine")
	@Enumerated(EnumType.STRING)
	private cusineEnum cusine;

	public Item(String itemName, int price, cusineEnum cusine) {
		super();
		this.itemName = itemName;
		this.price = price;
		this.cusine = cusine;
	}

	@Override
	public String toString() {
		return "Items [id=" + id + ", itemName=" + itemName + ", price=" + price + ", cusine=" + cusine + "]";
	}
	
	
	
}
