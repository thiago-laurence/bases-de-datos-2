package unlp.info.bd2.repositories;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends Store<Route> {
    List<Route> findByPriceLessThan(float price);
    List<Route> findByStops(Stop stop);

    List<Route> findByIdNotIn(List<Long> routes_ids);

    @Query("SELECT SIZE(s) FROM Route r JOIN r.stops s GROUP BY r.id ORDER BY SIZE(s) DESC")
    Long getMaxStopOfRoutes(PageRequest Pageable);

    @Query("SELECT r FROM Purchase p JOIN p.route r JOIN p.review rv GROUP BY r ORDER BY AVG(rv.rating) DESC")
    List<Route> findTop3RoutesWithMaxRating(PageRequest pageable);
}
