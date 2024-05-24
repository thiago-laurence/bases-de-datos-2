package unlp.info.bd2.repositories;
import unlp.info.bd2.model.User;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends Store<User> {
    Optional<User> findByUsername(String username);

    List<User> findFirst5ByOrderByPurchasesDesc();

    List<User> findByPurchases_TotalPriceGreaterThanEqual(float mount);
}