package unlp.info.bd2.repositories;

import unlp.info.bd2.model.User;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.utils.ToursException;

import java.util.Optional;


public interface ToursRepository {

    Optional<User> getUserById(Long id);
    Optional<Supplier> getSupplierById(Long id);
}
