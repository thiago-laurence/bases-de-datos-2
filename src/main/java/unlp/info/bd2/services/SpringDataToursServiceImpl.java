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

    @Override
    @Transactional
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User newUser = new User(username, password, fullName, email, birthdate, phoneNumber);
        try{
            newUser = this.userRepository.save(newUser);
        }catch (DataIntegrityViolationException e){
            throw new ToursException("Constraint Violation");
        }

        return newUser;
    }

    @Override
    @Transactional
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        DriverUser newDriverUser = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);

        return (DriverUser) this.driverUserRepository.save(newDriverUser);
    }

    @Override
    @Transactional
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser newTourGuide = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);

        return (TourGuideUser) this.tourGuideUserRepository.save(newTourGuide);
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
    public Stop createStop(String name, String description) throws ToursException {
        return null;
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        return List.of();
    }

    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        return null;
    }

    @Override
    public Optional<Route> getRouteById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return List.of();
    }

    @Override
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {

    }

    @Override
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {

    }

    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        return null;
    }

    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        return null;
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
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        Purchase purchase = new Purchase(code, route, user);
        if (this.purchaseRepository.countUsersRouteInDate(new Date(), route) == route.getMaxNumberUsers()){
            throw new ToursException("No puede realizarse la compra");
        }
        user.addPurchase(purchase);
        this.purchaseRepository.save(purchase);

        return purchase;
    }

    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        if (this.purchaseRepository.countUsersRouteInDate(date, route) == route.getMaxNumberUsers()){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, date, route, user);
        user.addPurchase(purchase);
        this.purchaseRepository.save(purchase);

        return purchase;
    }

    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        return null;
    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code) {
        return this.purchaseRepository.findByCode(code);
    }

    @Override
    public void deletePurchase(Purchase purchase) throws ToursException {

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
