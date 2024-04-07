package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ToursRepositoryImpl implements ToursRepository{

    @Autowired
    private SessionFactory sessionFactory;

}