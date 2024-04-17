package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Optional;

@Repository
public class ToursRepositoryImpl implements ToursRepository{

    @Autowired
    private SessionFactory sessionFactory;

    // *********** USER *************
    @Override @Transactional
    public void createUser(User user){
        this.sessionFactory.getCurrentSession().persist(user);
    }
    @Override @Transactional
    public User updateUser(User user){
        return (
                this.sessionFactory.getCurrentSession().merge(user)
        );
    }
    @Override @Transactional
    public void deleteUser(User user){
        this.sessionFactory.getCurrentSession().remove(user);
    }
    @Override @Transactional(readOnly=true)
    public Optional<User> getUserById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(User.class, id))
        );
    }
    @Override @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                                "FROM User WHERE username = :username", User.class).setParameter("username", username)
                        .uniqueResult())
        );
    }


    // ************* TOUR GUIDE *************
    @Override @Transactional(readOnly = true)
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT t FROM TourGuideUser t JOIN t.reviews r WHERE r.rating = 1", TourGuideUser.class)
                        .getResultList()
        );
         
    }
    
    // ************* STOP *************
    @Override @Transactional
    public void createStop(Stop stop){
        this.sessionFactory.getCurrentSession().persist(stop);
    }
    @Override @Transactional(readOnly = true)
    public Optional<Stop> getStopByName(String name){
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                                "FROM Stop WHERE name = :stop", Stop.class).setParameter("stop", name)
                        .uniqueResult())
        );
    }
    @Override @Transactional(readOnly = true)
    public Optional<Stop> getStopById(Long id){
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Stop.class, id))
        );
    }
    @Override @Transactional(readOnly = true)
    public List<Stop> getStopByNameStart(String name){
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "FROM Stop WHERE name LIKE :name", Stop.class).setParameter("name", name + "%")
                        .getResultList()
        );
    }
    @Override @Transactional(readOnly = true)
    public List<Route> getRoutesBelowPrice(float price) {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "FROM Route WHERE price < :price", Route.class).setParameter("price", price)
                        .getResultList()
        );
    }
    @Override @Transactional(readOnly = true)
    public Long getMaxStopOfRoutes() {
        return sessionFactory.getCurrentSession()
        .createQuery("select max(size(r.stops)) from Route r", Long.class)
        .uniqueResult();
    }


    // ************* ROUTE *************
    @Override @Transactional
    public void createRoute(Route route){
        this.sessionFactory.getCurrentSession().persist(route);
    }
    @Override @Transactional
    public void updateRoute(Route route){
        this.sessionFactory.getCurrentSession().merge(route);
    }
    @Override @Transactional(readOnly = true)
    public Optional<Route> getRouteByName(String name){
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                                "FROM Route WHERE name = :route", Route.class).setParameter("route", name)
                        .uniqueResult())
        );
    }
    @Override @Transactional(readOnly = true)
    public Optional<Route> getRouteById(Long id){
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Route.class, id))
        );
    }
    @Override @Transactional(readOnly = true)
    public List<Route> getRoutesWithStop(Stop stop){
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT r FROM Route r JOIN r.stops s WHERE s = :stop", Route.class).setParameter("stop", stop)
                        .getResultList()
        );
    }

    // ************* PURCHASE *************
    @Override @Transactional
    public void createPurchase(Purchase purchase){
        this.sessionFactory.getCurrentSession().persist(purchase);
    }
    @Override @Transactional
    public Purchase updatePurchase(Purchase purchase) {
        return (Purchase) this.sessionFactory.getCurrentSession().merge(purchase);
    }

    @Override @Transactional
    public void deletePurchase(Purchase purchase) {
        this.sessionFactory.getCurrentSession().remove(purchase);
    }

    @Override @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseById(Long id) {
        return Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Purchase.class, id));
    }

    @Override @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseByCode(String code){
        return (
            Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                            "FROM Purchase WHERE code = :code", Purchase.class).setParameter("code", code)
                    .uniqueResult())
        );
    }



    // ************* SUPPLIER *************
    @Override @Transactional
    public void createSupplier(Supplier supplier){
        this.sessionFactory.getCurrentSession().persist(supplier);
    }
    @Override @Transactional(readOnly=true)
    public Optional<Supplier> getSupplierById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Supplier.class, id))
        );
    }
    @Override @Transactional(readOnly=true)
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                    "FROM Supplier WHERE authorizationNumber = :authorizationNumber", Supplier.class).setParameter("authorizationNumber", authorizationNumber)
            .uniqueResult())
        );
    }
    @Override @Transactional
    public void updateSupplier(Supplier supplier){
        this.sessionFactory.getCurrentSession().merge(supplier);
    }
    @Override @Transactional(readOnly=true)
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        // return the top N suppliers that appear in the most purchases
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT s FROM Supplier s JOIN s.services se JOIN se.items "
                        + " i JOIN i.purchase p GROUP BY s ORDER BY COUNT(p) DESC", Supplier.class)
                        .setMaxResults(n)
                        .list()
        );
    }

    // ************* SERVICE *************
    @Override @Transactional
    public void createService(Service service){
        this.sessionFactory.getCurrentSession().persist(service);
    }
    @Override @Transactional(readOnly=true)
    public Optional<Service> getServiceById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Service.class, id))
        );
    }
    @Override @Transactional(readOnly=true)
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                    "FROM Service WHERE name = :name AND supplier.id = :supplierId", Service.class)
                    .setParameter("name", name)
                    .setParameter("supplierId", id)
                    .uniqueResult())
        );
    }
    @Override @Transactional
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        Service service = this.sessionFactory.getCurrentSession().find(Service.class, id);
        if (service == null)
            throw new ToursException("No existe el producto");
        service.setPrice(newPrice);
        this.sessionFactory.getCurrentSession().merge(service);
        return service;
    }
    @Override @Transactional(readOnly=true)
    public Service getMostDemandedService() {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT s FROM Service s JOIN s.itemServiceList i GROUP BY s ORDER BY COUNT(i) DESC", Service.class)
                        .setMaxResults(1)
                        .uniqueResult()
        );
    }
    @Override @Transactional(readOnly=true)
    public List<Service> getServiceNoAddedToPurchases() {
        return (
            this.sessionFactory.getCurrentSession().createQuery(
                    "SELECT s FROM Service s WHERE NOT EXISTS " +
                    "(SELECT i FROM ItemService i WHERE i.service = s AND i.purchase IS NOT NULL)", Service.class)
                    .getResultList()
        );
    }


    // ************* ITEM SERVICE *************
    @Override @Transactional
    public void createItemService(ItemService itemService){
        this.sessionFactory.getCurrentSession().persist(itemService);
    }
    @Override @Transactional(readOnly=true)
    public Optional<ItemService> getItemServiceById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(ItemService.class, id))
        );
    }

    // ******** CONSULTAS *************
    @Override @Transactional(readOnly = true)
    public List<User> findTop5UsersByNumberOfPurchases() {
        return this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT u FROM User u " +
                                "JOIN u.purchaseList p " +
                                "GROUP BY u " +
                                "ORDER BY COUNT(p) DESC", User.class)
                .setMaxResults(5)
                .list();
    }

    @Override @Transactional(readOnly = true)
    public long countPurchasesBetweenDates(Date startDate, Date endDate) {
        return this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT COUNT(p) FROM Purchase p WHERE p.date >= :startDate AND p.date <= :endDate", Long.class) // Agregar el tipo Long.class aquí
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }

    @Override @Transactional(readOnly = true)
    public List<Purchase> findTop10MoreExpensivePurchasesInServices() {
        return this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT p FROM Purchase p " +
                                "JOIN p.itemServiceList i " +
                                "GROUP BY p " +
                                "ORDER BY SUM(i.quantity * i.service.price) DESC", Purchase.class)
                .setMaxResults(10)
                .list();
    }












}