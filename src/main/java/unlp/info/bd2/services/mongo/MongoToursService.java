package unlp.info.bd2.services.mongo;

import unlp.info.bd2.documents.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MongoToursService {

    User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException;
    DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException;
    TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException;
    Optional<User> getUserById(String id) throws ToursException;
    Optional<User> getUserByUsername(String username) throws ToursException;
    User updateUser(User user) throws ToursException;
    void deleteUser(User user) throws ToursException;
    Stop createStop(String name, String description) throws ToursException;
    List<Stop> getStopByNameStart(String name);
    Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException;
    Optional<Route> getRouteById(String id);
    List<Route> getRoutesBelowPrice(float price);
    void assignDriverByUsername(String username, String idRoute) throws ToursException;
    void assignTourGuideByUsername(String username, String idRoute) throws ToursException;
    Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException;
    Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException;
    Service updateServicePriceById(String id, float newPrice) throws ToursException;
    Optional<Supplier> getSupplierById(String id);
    Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber);
    Optional<Service> getServiceByNameAndSupplierId(String name, String supplierId) throws ToursException;
    Purchase createPurchase(String code, Route route, User user) throws ToursException;
    Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException;
    ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException;
    Optional<Purchase> getPurchaseByCode(String code);
    void deletePurchase(Purchase purchase) throws ToursException;
    Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException;

    // QUERIES
    List<Purchase> getAllPurchasesOfUsername(String username);
    List<User> getUserSpendingMoreThan(float mount);
    List<Supplier> getTopNSuppliersInPurchases(int n);
    List<Purchase> getTop10MoreExpensivePurchasesInServices();
    List<User> getTop5UsersMorePurchases();
    long getCountOfPurchasesBetweenDates(Date start, Date end);
    List<Route> getRoutesWithStop(Stop stop);
    Long getMaxStopOfRoutes();
    List<Route> getRoutsNotSell();
    List<Route> getTop3RoutesWithMaxRating();
    Service getMostDemandedService();
    List<Service> getServiceNoAddedToPurchases();
    List<TourGuideUser> getTourGuidesWithRating1();

}
