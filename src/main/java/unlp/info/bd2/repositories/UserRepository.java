package unlp.info.bd2.repositories;
import unlp.info.bd2.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);
    void delete(User user);
    Optional<User> findByUsername(String username);
}
