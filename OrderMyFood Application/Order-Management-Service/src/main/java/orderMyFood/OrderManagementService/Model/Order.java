package orderMyFood.OrderManagementService.Model;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orders_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Long id;
	
	@Column(name = "date_of_order_placed",updatable = false)
	@CreationTimestamp
	private Date orderPlacedOn;
	
	@Column(name = "last_updated_on")
	@UpdateTimestamp
	@Builder.Default
	private Date LastUpdatedOn = new Date();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "order_fk", referencedColumnName = "order_id")
	private List<OrderItems> orderItems;
	
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@Column(name = "Total_price")
	private Integer totalPrice;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "phone_number")
	private String phone;
	
	@Column(name = "customer_address")
	private String address;
	

	

	
	
	
}
