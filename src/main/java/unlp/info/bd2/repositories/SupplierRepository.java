package unlp.info.bd2.repositories;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import unlp.info.bd2.model.Supplier;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends Store<Supplier> {
    Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);

    @Query("SELECT s " +
            "FROM Supplier s " +
            "JOIN s.services ser " +
            "JOIN ser.items isv " +
            "GROUP BY s.id " +
            "ORDER BY COUNT(isv.id) DESC")
    List<Supplier> findTopNSuppliersInPurchases(Pageable pageable);

}
