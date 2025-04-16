package unlp.info.bd2.services.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import unlp.info.bd2.documents.*;
import unlp.info.bd2.repositories.mongo.MongoToursRepository;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class MongoToursServiceImpl implements MongoToursService {

    @Autowired
    private final MongoToursRepository toursRepository;

    public MongoToursServiceImpl(MongoToursRepository toursRepository) {
        this.toursRepository = toursRepository;
    }

    @Override
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User user = new User(username, password, fullName, email, birthdate, phoneNumber);
        this.toursRepository.save(user);

        return user;
    }

    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        DriverUser driver = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
        this.toursRepository.save(driver);

        return driver;
    }

    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser tourGuide = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
        this.toursRepository.save(tourGuide);

        return tourGuide;
    }

    @Override
    public Optional<User> getUserById(String id) throws ToursException {
        return (
                this.toursRepository.getUserById(id)
        );
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return (
                this.toursRepository.getUserByUsername(username)
        );
    }

    @Override
    public User updateUser(User user) throws ToursException {
        return (
                (User) this.toursRepository.merge(user)
        );
    }

    @Override
    public void deleteUser(User user) throws ToursException {
        if (!user.isActive()){
            throw new ToursException("El usuario se encuentra desactivado");
        }

        if (user.isDeletable()){
            this.toursRepository.remove(user);
            return;
        }

        if (!user.isBaneable()){
            throw new ToursException("El usuario no puede ser desactivado");
        }

        user.setActive(false);
        this.toursRepository.merge(user);
    }

    @Override
    public Stop createStop(String name, String description) throws ToursException {
        Stop stop = new Stop(name, description);
        this.toursRepository.save(stop);

        return stop;
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        return (
                this.toursRepository.getStopByNameStart(name)
        );
    }

    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        Route route = new Route(name, price, totalKm, maxNumberOfUsers, stops);
        this.toursRepository.save(route);

        return route;
    }

    @Override
    public Optional<Route> getRouteById(String id) {
        return (
                this.toursRepository.getRouteById(id)
        );
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return (
                this.toursRepository.getRoutesBelowPrice(price)
        );
    }

    @Override
    public void assignDriverByUsername(String username, String idRoute) throws ToursException {
        Optional<DriverUser> opUser = this.toursRepository.getDriverUserByUsername(username);
        Optional<Route> opRoute = this.toursRepository.getRouteById(idRoute);
        if (opUser.isEmpty()){
            throw new ToursException("El 'Usuario' no existe");
        }
        if (opRoute.isEmpty()){
            throw new ToursException("La 'Ruta' no existe");
        }

        DriverUser driver = opUser.get();
        Route route = opRoute.get();
        route.addDriver(driver);
        driver.addRoute(route);

        this.toursRepository.merge(route);
        this.toursRepository.merge(driver);
    }

    @Override
    public void assignTourGuideByUsername(String username, String idRoute) throws ToursException {
        Optional<TourGuideUser> opUser = this.toursRepository.getTourGuideUserByUsername(username);
        Optional<Route> opRoute = this.toursRepository.getRouteById(idRoute);
        if (opUser.isEmpty()){
            throw new ToursException("El 'Usuario' no existe");
        }
        if (opRoute.isEmpty()){
            throw new ToursException("La 'Ruta' no existe");
        }

        TourGuideUser tourGuide = opUser.get();
        Route route = opRoute.get();
        tourGuide.addRoute(route);
        route.addTourGuide(tourGuide);

        this.toursRepository.merge(route);
        this.toursRepository.merge(tourGuide);
    }

    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        Supplier supplier = new Supplier(businessName, authorizationNumber);
        this.toursRepository.save(supplier);

        return supplier;
    }

    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        Service service = new Service(name, price, description);
        service.setSupplier(supplier);
        this.toursRepository.save(service);

        supplier.addService(service);
        this.toursRepository.merge(supplier);

        return service;
    }

    @Override
    public Service updateServicePriceById(String id, float newPrice) throws ToursException {
        Optional<Service> service = this.toursRepository.getServiceById(id);
        if (service.isEmpty()){
            throw new ToursException("No existe el Servicio buscado");
        }

        service.get().setPrice(newPrice);
        return (
                (Service) this.toursRepository.merge(service.get())
        );
    }

    @Override
    public Optional<Supplier> getSupplierById(String id) {
        return (
                toursRepository.getSupplierById(id)
        );
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return toursRepository.getSupplierByAuthorizationNumber(authorizationNumber);
    }

    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, String supplierId) throws ToursException {
        return toursRepository.getServiceByNameAndSupplierId(name, supplierId);
    }

    @Override
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        return this.createPurchase(code, new Date(), route, user);
    }

    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        if (this.toursRepository.countUsersRouteInDate(date, route) == route.getMaxNumberUsers()){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, date, route, user);
        this.toursRepository.save(purchase);

        user.addPurchase(purchase);
        this.toursRepository.merge(user);

        return purchase;
    }

    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        ItemService itemService = new ItemService(quantity, purchase, service);
        this.toursRepository.save(itemService);

        purchase.addItemService(itemService);
        service.addItemService(itemService);
        this.toursRepository.merge(purchase);
        this.toursRepository.merge(service);

        return itemService;
    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code) {
        return this.toursRepository.getPurchaseByCode(code);
    }

    @Override
    public void deletePurchase(Purchase purchase) throws ToursException {
        this.toursRepository.remove(purchase);
    }

    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        Review review = new Review(rating, comment, purchase);
        this.toursRepository.save(review);

        purchase.setReview(review);
        this.toursRepository.merge(purchase);

        return review;
    }

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        return (
                this.toursRepository.getAllPurchasesOfUsername(username)
        );
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount) {
        return this.toursRepository.getUserSpendingMoreThan(mount);
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return (
                this.toursRepository.getTopNSuppliersInPurchases(n)
        );
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices()  {
        return (
                toursRepository.getTop10MoreExpensivePurchasesInServices()
        );
    }

    @Override
    public List<User> getTop5UsersMorePurchases() {
        return (
                toursRepository.findTop5UsersByNumberOfPurchases()
        );
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return (
                toursRepository.countPurchasesBetweenDates(start, end)
        );
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        return (
                this.toursRepository.getRoutesWithStop(stop)
        );
    }

    @Override
    public Long getMaxStopOfRoutes() {
        return (
                this.toursRepository.getMaxStopOfRoutes()
        );
    }

    @Override
    public List<Route> getRoutsNotSell() {
        return (
                this.toursRepository.getRoutsNotSell()
        );
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        return (
                this.toursRepository.getTop3RoutesWithMaxRating()
        );
    }

    @Override
    public Service getMostDemandedService() {
        return (
                this.toursRepository.getMostDemandedService()
        );
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return (
                this.toursRepository.getServiceNoAddedToPurchases()
        );
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return (
                this.toursRepository.getTourGuidesWithRating1()
        );
    }
}
