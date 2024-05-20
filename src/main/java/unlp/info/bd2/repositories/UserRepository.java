package unlp.info.bd2.repositories;
import unlp.info.bd2.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

<<<<<<< HEAD
    List<User> findTop5ByPurchaseListIsNotNullOrderByPurchaseListDesc();

=======
    List<User> findFirst5ByOrderByPurchasesDesc();
>>>>>>> 3f4cad182446414c3a77b6852d4c82309763c547

    List<User> findByPurchases_TotalPriceGreaterThanEqual(float mount);
}
