package unlp.info.bd2.repositories;
import unlp.info.bd2.model.Stop;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends Store<Stop> {
    List<Stop> findByNameStartsWith(String name);
}
