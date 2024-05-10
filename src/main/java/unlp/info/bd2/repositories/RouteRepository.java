package unlp.info.bd2.repositories;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
    List<Route> findByPriceLessThan(float price);
    List<Route> findByStops(Stop stop);
    @Query("SELECT r FROM Route r WHERE r.id NOT IN (SELECT p.route.id FROM Purchase p)")
    List<Route> findRoutsNotSell();
    @Query("SELECT SIZE(s) FROM Route r JOIN r.stops s GROUP BY r.id ORDER BY SIZE(s) DESC LIMIT 1")
    Long getMaxStopOfRoutes(PageRequest Pageable);
    @Query("SELECT r FROM Purchase p JOIN p.route r JOIN p.review rv GROUP BY r ORDER BY AVG(rv.rating) DESC")
    List<Route> findTop3RoutesWithMaxRating(PageRequest pageable);
}
