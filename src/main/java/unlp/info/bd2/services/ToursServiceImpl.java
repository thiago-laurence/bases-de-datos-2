package unlp.info.bd2.services;

import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.ToursRepository;
import unlp.info.bd2.repositories.ToursRepositoryImpl;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class ToursServiceImpl implements ToursService{

    @Autowired
    private final ToursRepository toursRepository;

    public ToursServiceImpl(ToursRepository repository) throws ToursException {
        this.toursRepository = repository;
    }

    public ToursServiceImpl() {
        this.toursRepository = new ToursRepositoryImpl();
    }

    private void existsUsername(String username) throws ToursException {
        if (this.toursRepository.getUserByUsername(username).isPresent()){
            throw new ToursException("El 'nombre de usuario' ya se encuentra registrado");
        }
    }

    @Override
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User user = new User(username, password, fullName, email, birthdate, phoneNumber);
        this.toursRepository.createUser(user);

        return user;
    }

    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        DriverUser driver = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
        this.toursRepository.createUser(driver);

        return driver;
    }

    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser tourGuide = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
        this.toursRepository.createUser(tourGuide);

        return tourGuide;
    }

    @Override
    public Optional<User> getUserById(Long id) throws ToursException {
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
                this.toursRepository.updateUser(user)
        );
    }

    @Override
    public void deleteUser(User user) throws ToursException {
        if (!user.isActive()){
            throw new ToursException("El usuario se encuentra desactivado");
        }

        if (user.isDeleteable()){
            this.toursRepository.deleteUser(user);
            return;
        }

        if (!user.isBaneable()){
            throw new ToursException("El usuario no puede ser desactivado");
        }
        user.setActive(false);
        this.updateUser(user);
    }

    @Override
    public Stop createStop(String name, String description) throws ToursException {
        if (this.toursRepository.getStopByName(name).isPresent()){
            throw new ToursException("El 'nombre de la parada' ya se encuentra registrado");
        }
        Stop stop = new Stop(name, description);
        this.toursRepository.createStop(stop);

        return stop;
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        return this.toursRepository.getStopByNameStart(name);
    }

    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        if (this.toursRepository.getRouteByName(name).isPresent()){
            throw new ToursException("El 'nombre de la ruta' ya se encuentra registrado");
        }
        Route route = new Route(name, price, totalKm, maxNumberOfUsers, stops);
        this.toursRepository.createRoute(route);

        return route;
    }

    @Override
    public Optional<Route> getRouteById(Long id) {
        return this.toursRepository.getRouteById(id);
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return this.toursRepository.getRoutesBelowPrice(price);
    }

    @Override
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        Optional<User> opUser = this.toursRepository.getUserByUsername(username);
        Optional<Route> opRoute = this.toursRepository.getRouteById(idRoute);
        if (opUser.isEmpty()){
            throw new ToursException("El 'Usuario' no existe");
        }
        if (opRoute.isEmpty()){
            throw new ToursException("La 'Ruta' no existe");
        }

        DriverUser driver = (DriverUser) opUser.get();
        Route route = opRoute.get();
        route.addDriver(driver);
        driver.addRoute(route);
        this.toursRepository.updateUser(driver);
        this.toursRepository.updateRoute(route);
    }

    @Override
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        Optional<User> opUser = this.toursRepository.getUserByUsername(username);
        Optional<Route> opRoute = this.toursRepository.getRouteById(idRoute);
        if (opUser.isEmpty()){
            throw new ToursException("El 'Usuario' no existe");
        }
        if (opRoute.isEmpty()){
            throw new ToursException("La 'Ruta' no existe");
        }

        TourGuideUser tourGuide = (TourGuideUser) opUser.get();
        Route route = opRoute.get();
        tourGuide.addRoute(route);
        route.addTourGuide(tourGuide);
        this.toursRepository.updateUser(tourGuide);
        this.toursRepository.updateRoute(route);
    }

    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        if (this.toursRepository.getSupplierByAuthorizationNumber(authorizationNumber).isPresent()){
            throw new ToursException("El 'numero de autorizacion' ya se encuentra registrado");
        }
        Supplier supplier = new Supplier(businessName, authorizationNumber);
        this.toursRepository.createSupplier(supplier);
        return supplier;
    }

    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        Service service = new Service(name, price, description);
        supplier.addService(service);
        this.toursRepository.createService(service);
        this.toursRepository.updateSupplier(supplier);
        return service;
    }

    @Override
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        return this.toursRepository.updateServicePriceById(id, newPrice);
    }

    @Override
    public Optional<Supplier> getSupplierById(Long id) {
        return toursRepository.getSupplierById(id);
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return toursRepository.getSupplierByAuthorizationNumber(authorizationNumber);
    }

    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return toursRepository.getServiceByNameAndSupplierId(name, id);
    }

    private void checkLegalPurchase(String code, Route route) throws ToursException {
        if (this.toursRepository.getPurchaseByCode(code).isPresent())
            throw new ToursException("Constraint Violation");
        if (this.toursRepository.routeIsAvailable(route))
            throw new ToursException("No puede realizarse la compra");
    }

    private void persistUserAndPurchase(User user, Purchase purchase) throws ToursException {
        user.addPurchase(purchase);
        this.toursRepository.createPurchase(purchase);
        this.toursRepository.updateUser(user);
    }
     
    @Override
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        checkLegalPurchase(code, route);
        Purchase purchase = new Purchase(code, route, user);
        persistUserAndPurchase(user, purchase);
        return purchase;
    }

    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        checkLegalPurchase(code, route);
        Purchase purchase = new Purchase(code, date, route, user);
        persistUserAndPurchase(user, purchase);
        return purchase;
    }

    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException { //ver si ponemos validaciones en los metodos para los parametros
        ItemService itemService = new ItemService(quantity,purchase,service);
        this.toursRepository.createItemService(itemService);
        purchase.addItemService(itemService);
        this.toursRepository.updatePurchase(purchase);
        return itemService;
    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code) {
        return this.toursRepository.getPurchaseByCode(code);
    }

    @Override
    public void deletePurchase(Purchase purchase) throws ToursException {

    }

    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        Review review = new Review(rating, comment, purchase);
        this.toursRepository.createReview(review, purchase);
        return review;
    }

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        return this.toursRepository.getAllPurchasesOfUsername(username);
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount) {
        return this.toursRepository.getUserSpendingMoreThan(mount);
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return this.toursRepository.getTopNSuppliersInPurchases(n);
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices()  {
        return toursRepository.findTop10MoreExpensivePurchasesInServices();
    }



    @Override
    public List<User> getTop5UsersMorePurchases() {
        return toursRepository.findTop5UsersByNumberOfPurchases();
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return toursRepository.countPurchasesBetweenDates(start,end);
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        return this.toursRepository.getRoutesWithStop(stop);
    }

    @Override
    public Long getMaxStopOfRoutes() {
        return this.toursRepository.getMaxStopOfRoutes();
    }

    @Override
    public List<Route> getRoutsNotSell() {
        return this.toursRepository.getRoutsNotSell();
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        return this.toursRepository.getTop3RoutesWithMaxRating();
    }

    @Override
    public Service getMostDemandedService() {
        return this.toursRepository.getMostDemandedService();
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return this.toursRepository.getServiceNoAddedToPurchases();
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return this.toursRepository.getTourGuidesWithRating1();
    }
}
