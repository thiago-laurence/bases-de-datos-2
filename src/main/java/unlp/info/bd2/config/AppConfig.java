package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import unlp.info.bd2.services.hibernate.HibernateToursService;
import unlp.info.bd2.services.hibernate.HibernateToursServiceImpl;
import unlp.info.bd2.repositories.hibernate.HibernateToursRepository;
import unlp.info.bd2.repositories.hibernate.HibernateToursRepositoryImpl;

import unlp.info.bd2.repositories.mongo.MongoToursRepository;
import unlp.info.bd2.repositories.mongo.MongoToursRepositoryImpl;
import unlp.info.bd2.services.mongo.MongoToursService;
import unlp.info.bd2.services.mongo.MongoToursServiceImpl;

import unlp.info.bd2.utils.ToursException;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public MongoToursService createService() throws ToursException {
        MongoToursRepository repository = this.createRepository();
        return new MongoToursServiceImpl(repository);
    }

    @Bean
    @Primary
    public MongoToursRepository createRepository() {
        return new MongoToursRepositoryImpl();
    }

//    @Bean
//    @Primary
//    public HibernateToursService createService() throws ToursException {
//        HibernateToursRepository repository = this.createRepository();
//        return new HibernateToursServiceImpl(repository);
//    }
//
//    @Bean
//    @Primary
//    public HibernateToursRepository createRepository() {
//        return new HibernateToursRepositoryImpl();
//    }
}
