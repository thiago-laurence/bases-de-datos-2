package unlp.info.bd2.repositories;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Supplier;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    Supplier save(Supplier supplier);
    void delete(Supplier supplier);
    Optional<Supplier> findById(Long id);
    Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);
}
