package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.DriverUser;

import java.util.Optional;

@Repository
public interface DriverUserRepository extends CrudRepository<DriverUser, Long> {
    Optional<DriverUser> findByUsername(String username);
}
