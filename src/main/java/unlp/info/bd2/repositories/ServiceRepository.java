package unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {
    Service save(Service service);
    void delete(Service service);
    Optional<Service> findByNameAndSupplierId(String name, Long id);
    @Query("SELECT s FROM Service s WHERE s NOT IN (SELECT i.service FROM ItemService i)")
    List<Service> findServiceNoAddedToPurchases();
}
