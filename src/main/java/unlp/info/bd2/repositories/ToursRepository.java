package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.Optional;

public interface ToursRepository {

    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    void createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);

    void createStop(Stop stop);
    Optional<Stop> getStopByName(String name);

    void createRoute(Route route);
    Optional<Route> getRouteByName(String name);
    Optional<Route> getRouteById(Long id);

    void createPurchase(Purchase purchase);

    Optional<Purchase> getPurchaseByCode(String code);
    Optional<Supplier> getSupplierById(Long id);
    Optional<Service> getServiceById(Long id);
    Optional<ItemService> getItemServiceById(Long id);
    Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber);
    Optional<Service> getServiceByNameAndSupplierId(String name, Long id);
}
