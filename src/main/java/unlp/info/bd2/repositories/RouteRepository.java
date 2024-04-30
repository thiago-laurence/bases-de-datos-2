package unlp.info.bd2.repositories;
import unlp.info.bd2.model.Route;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
    Route save(Route route);
    void delete(Route route);
    Optional<Route> findById(long id);
}
