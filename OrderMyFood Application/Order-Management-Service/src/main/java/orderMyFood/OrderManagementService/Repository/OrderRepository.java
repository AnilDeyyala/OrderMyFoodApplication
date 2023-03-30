package orderMyFood.OrderManagementService.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import orderMyFood.OrderManagementService.Model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Modifying
    @Query(value = "DELETE Orders_table c WHERE c.order_id = ?1", nativeQuery = true)
    public void deleteByIdWithJPQL(Long id);
}
