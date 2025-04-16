package unlp.info.bd2.repositories.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.models.*;
import unlp.info.bd2.utils.ToursException;

@Repository
public class HibernateToursRepositoryImpl implements HibernateToursRepository {

    @Autowired
    private SessionFactory sessionFactory;

    // *********** OPERATIONS *************
    @Override
    public void save(Object object) throws ToursException {
        try{
            this.sessionFactory.getCurrentSession().persist(object);
        }catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation");
        }
    }

    @Override
    public void remove(Object object) {
        this.sessionFactory.getCurrentSession().remove(object);
    }

    @Override
    public Object merge(Object object) throws ToursException{
        try{
            return this.sessionFactory.getCurrentSession().merge(object);
        }catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation");
        }
    }

    // *********** USER *************
    @Override
    public Optional<User> getUserById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(User.class, id))
        );
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                    "FROM User WHERE username = :username", User.class).setParameter("username", username)
                    .uniqueResultOptional()
        );
    }

    @Override
    public Optional<TourGuideUser> getTourGuideUserByUsername(String username) {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                                "FROM TourGuideUser WHERE username = :username", TourGuideUser.class).setParameter("username", username)
                        .uniqueResultOptional()
        );
    }

    @Override
    public Optional<DriverUser> getDriverUserByUsername(String username) {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                                "FROM DriverUser WHERE username = :username", DriverUser.class).setParameter("username", username)
                        .uniqueResultOptional()
        );
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount){
        return (
            this.sessionFactory.getCurrentSession().createQuery(
                    "FROM User u JOIN u.purchaseList p WHERE p.totalPrice >= :mount", User.class)
                    .setParameter("mount", mount).getResultList()
        );
    }

    @Override
    public List<User> findTop5UsersByNumberOfPurchases() {
        return this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT u FROM User u " +
                                "JOIN u.purchaseList p " +
                                "GROUP BY u " +
                                "ORDER BY COUNT(p) DESC", User.class)
                .setMaxResults(5)
                .getResultList();
    }

    // ************* TOUR GUIDE *************
    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                                "SELECT t FROM Purchase p JOIN p.review rv "
                                        + "JOIN p.route.tourGuideList t "
                                        + "WHERE rv.rating = 1", TourGuideUser.class)
                        .getResultList()
        );
    }
    
    // ************* STOP *************
    @Override
    public List<Stop> getStopByNameStart(String name){
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "FROM Stop WHERE name LIKE :name", Stop.class).setParameter("name", name + "%")
                        .getResultList()
        );
    }

    @Override
    public Long getMaxStopOfRoutes() {
        return
                sessionFactory.getCurrentSession().createQuery(
                        "SELECT COUNT(s) FROM Route r JOIN r.stops s GROUP BY r ORDER BY COUNT(s) DESC", Long.class)
                    .setMaxResults(1)
                    .uniqueResult();
    }

    // ************* ROUTE *************

    @Override
    public Optional<Route> getRouteById(Long id){
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Route.class, id))
        );
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop){
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT r FROM Route r JOIN r.stops s WHERE s = :stop", Route.class).setParameter("stop", stop)
                        .getResultList()
        );
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT r FROM Purchase p JOIN p.route r JOIN p.review rv GROUP BY p.route  ORDER BY AVG(rv.rating) DESC", Route.class)
                        .setMaxResults(3)
                        .getResultList()
        );
    }

    @Override
    public List<Route> getRoutsNotSell() {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT r FROM Route r WHERE NOT EXISTS "
                        + "(SELECT p FROM Purchase p WHERE p.route = r)", Route.class)
                        .getResultList()
        );
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                                "FROM Route WHERE price < :price", Route.class).setParameter("price", price)
                        .getResultList()
        );
    }

    // ************* PURCHASE *************
    @Override
    public Optional<Purchase> getPurchaseByCode(String code){
        return this.sessionFactory.getCurrentSession().createSelectionQuery(
                "FROM Purchase p WHERE p.code = :code", Purchase.class).setParameter("code", code)
                .uniqueResultOptional()
        ;
    }

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username){
        return (
            this.sessionFactory.getCurrentSession().createQuery(
                    "FROM Purchase p JOIN p.user u WHERE u.username =: username", Purchase.class)
                    .setParameter("username", username).getResultList()
        );
    }

    @Override
    public long countUsersRouteInDate(Date date, Route route){
        return (
            this.sessionFactory.getCurrentSession().createQuery(
                    "SELECT COUNT(p) FROM Purchase p JOIN p.route r WHERE p.date =: date AND r.id =: route_id", Long.class)
                    .setParameter("route_id", route.getId()).setParameter("date", date).getSingleResult()
        );
    }

    @Override
    public long countPurchasesBetweenDates(Date startDate, Date endDate) {
        return this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT COUNT(p) FROM Purchase p WHERE p.date >= :startDate AND p.date <= :endDate", Long.class) // Agregar el tipo Long.class aquí
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        return this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT p FROM Purchase p WHERE p IN " +
                                "(SELECT i.purchase FROM ItemService i WHERE i.purchase IS NOT NULL) " +
                                "ORDER BY p.totalPrice DESC", Purchase.class)
                .setMaxResults(10)
                .getResultList();
    }

    // ************* SUPPLIER *************
    @Override
    public Optional<Supplier> getSupplierById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Supplier.class, id))
        );
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                    "FROM Supplier WHERE authorizationNumber = :authorizationNumber", Supplier.class).setParameter("authorizationNumber", authorizationNumber)
                        .uniqueResultOptional()
        );
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT s FROM Supplier s JOIN s.services se JOIN se.items i "
                        + "GROUP BY s ORDER BY COUNT(i) DESC", Supplier.class)
                        .setMaxResults(n)
                        .getResultList()
        );
    }

    // ************* SERVICE *************
    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                    "FROM Service WHERE name = :name AND supplier.id = :supplierId", Service.class)
                    .setParameter("name", name)
                    .setParameter("supplierId", id)
                    .uniqueResultOptional()
        );
    }

    @Override
    public Optional<Service> getServiceById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Service.class, id))
        );
    }

    @Override
    public Service getMostDemandedService() {
        return (
                this.sessionFactory.getCurrentSession().createQuery(
                        "SELECT s FROM Service s JOIN s.items i GROUP BY s ORDER BY SUM(i.quantity) DESC", Service.class)
                        .setMaxResults(1)
                        .uniqueResult()
        );
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("SELECT s FROM Service s WHERE size(s.items) = 0", Service.class)
                .getResultList();
    }

}