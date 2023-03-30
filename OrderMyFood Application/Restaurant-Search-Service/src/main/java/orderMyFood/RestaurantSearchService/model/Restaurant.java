package orderMyFood.RestaurantSearchService.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Restaurant_Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "Restaurant_name")
	private String name;
	
	@Column(name = "Location")
	private String location;
	
	@Column(name = "Distance_from_city_center")
	private int distance;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Restaurant_fk", referencedColumnName = "id")
	private List<Item> items;
	
	@Column(name = "Date_of_Established")
	@Temporal(TemporalType.DATE)
	@Builder.Default
	private Date DateEstablished = new Date();
	
	public Restaurant(Long id, String name, String location, int distance, List<Item> items) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.distance = distance;
		this.items = items;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", location=" + location + ", distance=" + distance
				+ ", items=" + items + ", DateEstablished=" + DateEstablished + "]";
	}
	
	
	
	
}
