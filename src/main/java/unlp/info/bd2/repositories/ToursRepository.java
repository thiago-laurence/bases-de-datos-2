package unlp.info.bd2.repositories;

import unlp.info.bd2.model.User;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Service;
import java.util.Optional;

public interface ToursRepository {

    Optional<User> getUserById(Long id);
    Optional<Supplier> getSupplierById(Long id);
    Optional<Service> getServiceById(Long id);
    Optional<ItemService> getItemServiceById(Long id);
}
