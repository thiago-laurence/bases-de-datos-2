package unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;

@Repository
public interface ServiceRepository extends Store<Service> {
    Optional<Service> findByNameAndSupplierId(String name, Long id);

    List<Service> findByItemsIsEmpty();

    @Query("SELECT s FROM Service s JOIN s.items i GROUP BY s ORDER BY SUM(i.quantity) DESC FETCH FIRST 1 ROWS ONLY")
    Service getMostDemandedService();
}
