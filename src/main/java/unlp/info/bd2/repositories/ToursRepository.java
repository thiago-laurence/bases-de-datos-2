package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.Optional;
import java.util.List;

public interface ToursRepository {
    void remove(Object object);
    Object merge(Object object) throws ToursException;
    void save(Object object) throws ToursException;

    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);

    Optional<Stop> getStopByName(String name);
    List<Stop> getStopByNameStart(String name);

    Optional<Route> getRouteByName(String name);
    Optional<Route> getRouteById(Long id);

    Optional<Service> getServiceById(Long id);

    List<TourGuideUser> getTourGuidesWithRating1();
    Long getMaxStopOfRoutes();
    List<Route> getRoutesBelowPrice(float price);
    List<Route> getRoutesWithStop(Stop stop);
    List<Route> getTop3RoutesWithMaxRating();
    List<Route> getRoutsNotSell();
    Optional<Purchase> getPurchaseByCode(String code);
    long countUsersRouteInDate(Date date, Route route);
    Optional<Supplier> getSupplierById(Long id);
    Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber);
    List<Supplier> getTopNSuppliersInPurchases(int n);
    Optional<Service> getServiceByNameAndSupplierId(String name, Long supplier);
    Service getMostDemandedService();
    List<Service> getServiceNoAddedToPurchases();
    List<User> findTop5UsersByNumberOfPurchases();
    long countPurchasesBetweenDates(Date startDate, Date endDate);
    List<Purchase> getTop10MoreExpensivePurchasesInServices();
    List<Purchase> getAllPurchasesOfUsername(String username);
    List<User> getUserSpendingMoreThan(float mount);
}