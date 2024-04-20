package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.Optional;
import java.util.List;

public interface ToursRepository {

    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    void createUser(User user) throws ToursException;
    User updateUser(User user);
    void deleteUser(User user);

    List<TourGuideUser> getTourGuidesWithRating1();

    void createStop(Stop stop);
    Optional<Stop> getStopByName(String name);
    Optional<Stop> getStopById(Long id);
    List<Stop> getStopByNameStart(String name);
    Long getMaxStopOfRoutes();

    void createRoute(Route route);
    void updateRoute(Route route);
    Optional<Route> getRouteByName(String name);
    Optional<Route> getRouteById(Long id);
    List<Route> getRoutesBelowPrice(float price);
    List<Route> getRoutesWithStop(Stop stop);
    List<Route> getTop3RoutesWithMaxRating();
    List<Route> getRoutsNotSell();

    void createPurchase(Purchase purchase) throws ToursException;
    void  updatePurchase(Purchase purchase);
    Optional<Purchase> getPurchaseById(Long id);
    Optional<Purchase> getPurchaseByCode(String code);
    long countUsersRouteInDate(Date date, Route route);

    void createReview(Review review, Purchase purchase);

    void createSupplier(Supplier supplier);
    Optional<Supplier> getSupplierById(Long id);
    Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber);
    void updateSupplier(Supplier supplier);
    List<Supplier> getTopNSuppliersInPurchases(int n);

    void createService(Service service);
    Optional<Service> getServiceById(Long id);
    Optional<Service> getServiceByNameAndSupplierId(String name, Long supplier);
    Service updateServicePriceById(Long id, float newPrice) throws ToursException;
    Service getMostDemandedService();
    List<Service> getServiceNoAddedToPurchases();

    void createItemService(ItemService itemService);
    Optional<ItemService> getItemServiceById(Long id);

    List<User> findTop5UsersByNumberOfPurchases();
    long countPurchasesBetweenDates(Date startDate, Date endDate);
    List<Purchase> getTop10MoreExpensivePurchasesInServices();
    List<Purchase> getAllPurchasesOfUsername(String username);
    List<User> getUserSpendingMoreThan(float mount);
    }
