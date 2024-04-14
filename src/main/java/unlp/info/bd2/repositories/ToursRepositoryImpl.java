package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;

import java.util.Date;
import java.util.Optional;

@Repository
public class ToursRepositoryImpl implements ToursRepository{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly=true)
    public Optional<User> getUserById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(User.class, id))
        );
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<Supplier> getSupplierById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Supplier.class, id))
        );
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<Service> getServiceById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Service.class, id))
        );
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<ItemService> getItemServiceById(Long id) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(ItemService.class, id))
        );
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Supplier.class, authorizationNumber))
        );
    }

    // TODO: Averiguar cómo buscar por 2 valores.
    @Override
    @Transactional(readOnly=true)
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) {
        return Optional.empty(); /*(
                //Optional.ofNullable(this.sessionFactory.getCurrentSession().
        );*/
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                                "FROM User WHERE username = :username", User.class).setParameter("username", username)
                        .uniqueResult())
        );
    }

    @Override
    @Transactional
    public void createUser(User user){
        this.sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    @Transactional
    public User updateUser(User user){
        return (
                this.sessionFactory.getCurrentSession().merge(user)
        );
    }

    @Override
    @Transactional
    public void deleteUser(User user){
        this.sessionFactory.getCurrentSession().remove(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Stop> getStopByName(String name){
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                                "FROM Stop WHERE name = :stop", Stop.class).setParameter("stop", name)
                        .uniqueResult())
        );
    }

    @Override
    @Transactional
    public void createStop(Stop stop){
        this.sessionFactory.getCurrentSession().persist(stop);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Route> getRouteByName(String name){
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                                "FROM Route WHERE name = :route", Route.class).setParameter("route", name)
                        .uniqueResult())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Route> getRouteById(Long id){
        return (
                Optional.ofNullable(this.sessionFactory.getCurrentSession().find(Route.class, id))
        );
    }

    @Override
    @Transactional
    public void createRoute(Route route){
        this.sessionFactory.getCurrentSession().persist(route);
    }

    @Override
    @Transactional
    public void createPurchase(Purchase purchase){
        this.sessionFactory.getCurrentSession().persist(purchase);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseByCode(String code){
        return (
            Optional.ofNullable(this.sessionFactory.getCurrentSession().createQuery(
                            "FROM Purchase WHERE code = :code", Purchase.class).setParameter("code", code)
                    .uniqueResult())
        );
    }
}