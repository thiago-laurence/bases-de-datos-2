package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Store<T> extends CrudRepository<T, Long> {
}
