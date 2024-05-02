package unlp.info.bd2.repositories;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
    Route save(Route route);
    void delete(Route route);
    Optional<Route> findById(long id);
    List<Route> findByPriceLessThan(float price);
    List<Route> findByStops(Stop stop);
    // Make a @Query to find all the routes that are not sold (no Purchase references to it))
    @Query("SELECT r FROM Route r WHERE r.id NOT IN (SELECT p.route.id FROM Purchase p)")
    List<Route> findRoutsNotSell();
}
