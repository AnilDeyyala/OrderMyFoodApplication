package orderMyFood.OrderManagementService.Model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Order_Items_Id")
	private Long id;
	
	@Column(name = "item_code", length = 16)
	private UUID itemCode;
	
	@Column(name = "Price")
	private Integer price;
	
	@Column(name = "Quantity")
	private Integer quantity;
	
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "order_fk")
//	Order order;
	

	public OrderItems(Integer price, Integer quantity) {
		super();
		this.price = price;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "orderItems [id=" + id + ", price=" + price + ", quantity=" + quantity + "]";
	}
	
	

}
