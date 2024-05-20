package unlp.info.bd2.repositories;
import unlp.info.bd2.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findTop5ByPurchaseListIsNotNullOrderByPurchaseListDesc();


    List<User> findByPurchases_TotalPriceGreaterThanEqual(float mount);
}
