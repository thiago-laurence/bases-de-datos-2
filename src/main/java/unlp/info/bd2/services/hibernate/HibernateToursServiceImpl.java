package unlp.info.bd2.services.hibernate;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import unlp.info.bd2.models.*;
import unlp.info.bd2.repositories.hibernate.HibernateToursRepository;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class HibernateToursServiceImpl implements HibernateToursService {

    @Autowired
    private final HibernateToursRepository toursRepository;

    public HibernateToursServiceImpl(HibernateToursRepository repository) throws ToursException {
        this.toursRepository = repository;
    }

    @Override
    @Transactional
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User user = new User(username, password, fullName, email, birthdate, phoneNumber);
        this.toursRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        DriverUser driver = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
        this.toursRepository.save(driver);

        return driver;
    }

    @Override
    @Transactional
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser tourGuide = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
        this.toursRepository.save(tourGuide);

        return tourGuide;
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) throws ToursException {
        System.out.println("Access to DB");
        return (
                this.toursRepository.getUserById(id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return (
                this.toursRepository.getUserByUsername(username)
        );
    }

    @Override
    @CachePut(value = "user", key = "#user.id")
    @Transactional
    public User updateUser(User user) throws ToursException {
        return (
                (User) this.toursRepository.merge(user)
        );
    }

    @Override
    @CacheEvict(value = "user", key = "#user.id")
    @Transactional
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
    @Transactional
    public Stop createStop(String name, String description) throws ToursException {
        Stop stop = new Stop(name, description);
        this.toursRepository.save(stop);

        return stop;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stop> getStopByNameStart(String name) {
        return this.toursRepository.getStopByNameStart(name);
    }

    @Override
    @Transactional
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        Route route = new Route(name, price, totalKm, maxNumberOfUsers, stops);
        this.toursRepository.save(route);

        return route;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Route> getRouteById(Long id) {
        return this.toursRepository.getRouteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesBelowPrice(float price) {
        return this.toursRepository.getRoutesBelowPrice(price);
    }

    @Override
    @Transactional
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
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
    }

    @Override
    @Transactional
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
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
    }

    @Override
    @Transactional
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        Supplier supplier = new Supplier(businessName, authorizationNumber);
        this.toursRepository.save(supplier);

        return supplier;
    }

    @Override
    @Transactional
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        Service service = new Service(name, price, description);
        supplier.addService(service);
        service.setSupplier(supplier);
        this.toursRepository.save(service);

        return service;
    }

    @Override
    @Transactional
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        Optional<Service> service = this.toursRepository.getServiceById(id);
        if (service.isEmpty()){
            throw new ToursException("No existe el Servicio buscado");
        }

        service.get().setPrice(newPrice);
        return (Service) this.toursRepository.merge(service.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierById(Long id) {
        return toursRepository.getSupplierById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return toursRepository.getSupplierByAuthorizationNumber(authorizationNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return toursRepository.getServiceByNameAndSupplierId(name, id);
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        return this.createPurchase(code, new Date(), route, user);
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        if (this.toursRepository.countUsersRouteInDate(date, route) == route.getMaxNumberUsers()){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, date, route, user);
        user.addPurchase(purchase);
        this.toursRepository.save(purchase);

        return purchase;
    }

    @Override
    @Transactional
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException { //ver si ponemos validaciones en los metodos para los parametros
        ItemService itemService = new ItemService(quantity, purchase, service);
        purchase.addItemService(itemService);
        service.addItemService(itemService);
        this.toursRepository.save(itemService);

        return itemService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseByCode(String code) {
        return this.toursRepository.getPurchaseByCode(code);
    }

    @Override
    @Transactional
    public void deletePurchase(Purchase purchase) throws ToursException {
        this.toursRepository.remove(purchase);
    }

    @Override
    @Transactional
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        Review review = new Review(rating, comment, purchase);
        purchase.setReview(review);
        this.toursRepository.save(review);

        return review;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        return this.toursRepository.getAllPurchasesOfUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUserSpendingMoreThan(float mount) {
        return this.toursRepository.getUserSpendingMoreThan(mount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return this.toursRepository.getTopNSuppliersInPurchases(n);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getTop10MoreExpensivePurchasesInServices()  {
        return toursRepository.getTop10MoreExpensivePurchasesInServices();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getTop5UsersMorePurchases() {
        return toursRepository.findTop5UsersByNumberOfPurchases();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return toursRepository.countPurchasesBetweenDates(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesWithStop(Stop stop) {
        return this.toursRepository.getRoutesWithStop(stop);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMaxStopOfRoutes() {
        return this.toursRepository.getMaxStopOfRoutes();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutsNotSell() {
        return this.toursRepository.getRoutsNotSell();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getTop3RoutesWithMaxRating() {
        return this.toursRepository.getTop3RoutesWithMaxRating();
    }

    @Override
    @Transactional(readOnly = true)
    public Service getMostDemandedService() {
        return this.toursRepository.getMostDemandedService();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Service> getServiceNoAddedToPurchases() {
        return this.toursRepository.getServiceNoAddedToPurchases();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return this.toursRepository.getTourGuidesWithRating1();
    }
}
