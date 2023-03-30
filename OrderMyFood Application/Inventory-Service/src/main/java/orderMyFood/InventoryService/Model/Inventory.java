package orderMyFood.InventoryService.Model;

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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "inventory_id")
	private Long id;
	
	
	@Column(name = "item_code", length = 16, unique = true)
	private UUID itemCode;
	
	@Column(name = "stock")
	private Long stock;
}
