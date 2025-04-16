package unlp.info.bd2.repositories.mongo;

import unlp.info.bd2.documents.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MongoToursRepository {
    void remove(Object object);
    Object merge(Object object) throws ToursException;
    void save(Object object) throws ToursException;

    Optional<User> getUserById(String id);
    Optional<User> getUserByUsername(String username);
    Optional<TourGuideUser> getTourGuideUserByUsername(String username);
    Optional<DriverUser> getDriverUserByUsername(String username);

    List<Stop> getStopByNameStart(String name);

    Optional<Route> getRouteById(String id);

    Optional<Service> getServiceById(String id);

    List<TourGuideUser> getTourGuidesWithRating1();
    Long getMaxStopOfRoutes();
    List<Route> getRoutesBelowPrice(float price);
    List<Route> getRoutesWithStop(Stop stop);
    List<Route> getTop3RoutesWithMaxRating();
    List<Route> getRoutsNotSell();
    Optional<Purchase> getPurchaseByCode(String code);
    long countUsersRouteInDate(Date date, Route route);
    Optional<Supplier> getSupplierById(String id);
    Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber);
    List<Supplier> getTopNSuppliersInPurchases(int n);
    Optional<Service> getServiceByNameAndSupplierId(String name, String supplierId);
    Service getMostDemandedService();
    List<Service> getServiceNoAddedToPurchases();
    List<User> findTop5UsersByNumberOfPurchases();
    long countPurchasesBetweenDates(Date startDate, Date endDate);
    List<Purchase> getTop10MoreExpensivePurchasesInServices();
    List<Purchase> getAllPurchasesOfUsername(String username);
    List<User> getUserSpendingMoreThan(float mount);
}