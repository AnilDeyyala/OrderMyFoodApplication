package orderMyFood.InventoryService.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import orderMyFood.InventoryService.Model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	
	Optional<Inventory> findByItemCode(UUID itemCode);
}
