package unlp.info.bd2.repositories;
import unlp.info.bd2.model.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends Store<Purchase> {
    Optional<Purchase> findByCode(String code);

    long countFindByRouteEqualsAndDateEquals(Route route, Date date);

    List<Purchase> findByUser_Username(String username);

    List<Purchase> findFirst10ByItemsIsNotEmptyOrderByTotalPriceDesc();

    long countByDateBetween(Date start, Date end);

    @Query("SELECT DISTINCT p.route.id FROM Purchase p")
    List<Long> findAllRoutesIds();
}
