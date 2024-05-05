package unlp.info.bd2.repositories;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    Optional<Purchase> findByCode(String code);

    @Query("SELECT COUNT(p) FROM Purchase p JOIN p.route r WHERE p.date = ?1 AND r = ?2")
    long countUsersRouteInDate(Date date, Route route);

    List<Purchase> findByUser_Username(String username);


    @Query("SELECT p FROM Purchase p WHERE p IN " +
                                "(SELECT i.purchase FROM ItemService i WHERE i.purchase IS NOT NULL) " +
                                "ORDER BY p.totalPrice DESC LIMIT 10")
    List<Purchase> getTop10MoreExpensivePurchasesInServices();

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.date BETWEEN :start AND :end")
    long getCountOfPurchasesBetweenDates(Date start, Date end);

    @Query("SELECT DISTINCT p.user " +
            "FROM Purchase p " +
            "GROUP BY p.user " +
            "ORDER BY COUNT(p) DESC " +
            "LIMIT 5")
    List<User> findTop5UsersMorePurchases();


}
