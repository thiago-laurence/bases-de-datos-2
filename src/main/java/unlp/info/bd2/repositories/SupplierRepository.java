package unlp.info.bd2.repositories;
import unlp.info.bd2.model.Supplier;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    Optional<Supplier> findById(Long id);
    Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);
}
