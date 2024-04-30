package unlp.info.bd2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class SpringDataToursServiceImpl implements ToursService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DriverUserRepository driverUserRepository;
    @Autowired
    private TourGuideUserRepository tourGuideUserRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private StopRepository stopRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ItemServiceRepository itemServiceRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User newUser = new User(username, password, fullName, email, birthdate, phoneNumber);
        try{
            this.userRepository.save(newUser);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return newUser;
    }

    @Override
    @Transactional
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        DriverUser newDriverUser = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
        try{
            this.userRepository.save(newDriverUser);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return newDriverUser;
    }

    @Override
    @Transactional
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser newTourGuide = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
        try{
            this.userRepository.save(newTourGuide);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return newTourGuide;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) throws ToursException {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return this.userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User updateUser(User user) throws ToursException {
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) throws ToursException {
        if (!user.isActive()){
            throw new ToursException("El usuario se encuentra desactivado");
        }

        if (user.isDeleteable()){
            this.userRepository.delete(user);
            return;
        }

        if (!user.isBaneable()){
            throw new ToursException("El usuario no puede ser desactivado");
        }

        user.setActive(false);
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public Stop createStop(String name, String description) throws ToursException {
        Stop newStop = new Stop(name, description);
        try{
            this.stopRepository.save(newStop);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return newStop;
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        return List.of();
    }

    @Override
    @Transactional
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        Route newRoute = new Route(name, price, totalKm, maxNumberOfUsers, stops);
        try{
            this.routeRepository.save(newRoute);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return newRoute;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Route> getRouteById(Long id) {
        return this.routeRepository.findById(id);
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return List.of();
    }

    @Override
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {

    }

    @Override
    @Transactional
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        Optional<User> opUser = this.userRepository.findByUsername(username);
        Optional<Route> opRoute = this.routeRepository.findById(idRoute);
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

        this.routeRepository.save(route);
    }

    @Override
    @Transactional
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        Supplier newSupplier = new Supplier(businessName, authorizationNumber);
        try{
            this.supplierRepository.save(newSupplier);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return newSupplier;
    }

    @Override
    @Transactional
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        Service service = new Service(name, price, description);
        supplier.addService(service);
        service.setSupplier(supplier);
        this.serviceRepository.save(service);

        return service;
    }

    @Override
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        return null;
    }

    @Override
    public Optional<Supplier> getSupplierById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return Optional.empty();
    }

    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        Purchase purchase = new Purchase(code, route, user);
        if (this.purchaseRepository.countUsersRouteInDate(new Date(), route) == route.getMaxNumberUsers()){
            throw new ToursException("No puede realizarse la compra");
        }
        user.addPurchase(purchase);
        try{
            this.purchaseRepository.save(purchase);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return purchase;
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        if (this.purchaseRepository.countUsersRouteInDate(date, route) == route.getMaxNumberUsers()){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, date, route, user);
        user.addPurchase(purchase);
        try{
            this.purchaseRepository.save(purchase);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return purchase;
    }


    @Override
    @Transactional
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        ItemService newItemService = new ItemService(quantity, purchase, service);
        purchase.addItemService(newItemService);
        service.addItemService(newItemService);
        this.itemServiceRepository.save(newItemService);

        return newItemService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseByCode(String code) {
        return this.purchaseRepository.findByCode(code);
    }

    @Override
    @Transactional
    public void deletePurchase(Purchase purchase) throws ToursException {
        this.purchaseRepository.delete(purchase);
    }

    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        return null;
    }

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        return List.of();
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount) {
        return List.of();
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return List.of();
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        return List.of();
    }

    @Override
    public List<User> getTop5UsersMorePurchases() {
        return List.of();
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return 0;
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        return List.of();
    }

    @Override
    public Long getMaxStopOfRoutes() {
        return 0L;
    }

    @Override
    public List<Route> getRoutsNotSell() {
        return List.of();
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        return List.of();
    }

    @Override
    public Service getMostDemandedService() {
        return null;
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return List.of();
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return List.of();
    }
}
