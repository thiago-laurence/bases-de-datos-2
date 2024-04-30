package unlp.info.bd2.repositories;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Supplier;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    Supplier save(Supplier supplier);
    void delete(Supplier supplier);
}
