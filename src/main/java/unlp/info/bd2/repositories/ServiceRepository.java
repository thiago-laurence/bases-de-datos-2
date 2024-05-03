package unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {
    Service save(Service service);
    void delete(Service service);
    Optional<Service> findByNameAndSupplierId(String name, Long id);
}
