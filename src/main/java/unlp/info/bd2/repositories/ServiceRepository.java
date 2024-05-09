package unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {
    Optional<Service> findByNameAndSupplierId(String name, Long id);

    @Query("SELECT s FROM Service s WHERE size(s.items) = 0")
    List<Service> findServiceNoAddedToPurchases();

    @Query("SELECT s FROM Service s JOIN s.items i GROUP BY s ORDER BY SUM(i.quantity) DESC FETCH FIRST 1 ROWS ONLY")
    Service getMostDemandedService();
}
