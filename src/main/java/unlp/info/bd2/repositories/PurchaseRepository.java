package unlp.info.bd2.repositories;
import org.springframework.data.domain.Pageable;
import unlp.info.bd2.model.*;

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

    List<Purchase> findByItemServiceListIsNotEmptyOrderByTotalPriceDesc(Pageable pageable);


    long countByDateBetween(Date start, Date end);



}
