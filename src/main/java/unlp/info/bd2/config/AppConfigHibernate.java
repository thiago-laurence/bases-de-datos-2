package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import unlp.info.bd2.services.hibernate.HibernateToursService;
import unlp.info.bd2.services.hibernate.HibernateToursServiceImpl;

import unlp.info.bd2.repositories.hibernate.HibernateToursRepository;
import unlp.info.bd2.repositories.hibernate.HibernateToursRepositoryImpl;

import unlp.info.bd2.utils.ToursException;

@Configuration
public class AppConfigHibernate {

    @Bean
    @Primary
    public HibernateToursService createService() throws ToursException {
        HibernateToursRepository repository = this.createRepository();
        return new HibernateToursServiceImpl(repository);
    }

    @Bean
    @Primary
    public HibernateToursRepository createRepository() {
        return new HibernateToursRepositoryImpl();
    }
}
