package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.Optional;

public interface ToursRepository {

    void createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);

    void createStop(Stop stop);
    Optional<Stop> getStopByName(String name);

    void createRoute(Route route);
    Optional<Route> getRouteByName(String name);
    Optional<Route> getRouteById(Long id);

    void createPurchase(Purchase purchase);
    Optional<Purchase> getPurchaseByCode(String code);

    void createSupplier(Supplier supplier);
    Optional<Supplier> getSupplierById(Long id);
    Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber);
    void updateSupplier(Supplier supplier);

    void createService(Service service);
    Optional<Service> getServiceById(Long id);
    Optional<Service> getServiceByNameAndSupplierId(String name, Long supplier);

    void createItemService(ItemService itemService);
    Optional<ItemService> getItemServiceById(Long id);
}
