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

    @Query("SELECT u FROM User u JOIN u.purchaseList p " +
            "GROUP BY u " +
            "ORDER BY SIZE(p) DESC LIMIT 5")
    List<User> getTop5UsersMorePurchasesTest();

    @Query("FROM User u JOIN u.purchaseList p WHERE p.totalPrice >= ?1")
    List<User> getUserSpendingMoreThan(float mount);
}
