package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.User;

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
}