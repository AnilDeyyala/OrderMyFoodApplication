package orderMyFood.OrderManagementService.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import orderMyFood.OrderManagementService.Model.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long>{

}
