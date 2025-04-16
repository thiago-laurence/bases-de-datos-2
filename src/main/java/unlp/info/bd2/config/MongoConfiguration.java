package unlp.info.bd2.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ComponentScan(basePackages = {
        "unlp.info.bd2.repositories.mongo",
        "unlp.info.bd2.services.mongo",
        "unlp.info.bd2.documents"
})
public class MongoConfiguration extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "bd2_tours_" + getGroupNumber();
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://AdminGrupo5:AdminGrupo5Password@localhost:27017/bd2_tours_" + getDatabaseName() + "?authSource=bd2_tours_5");
    }

    @Override
    protected boolean autoIndexCreation() { return true; }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    private int getGroupNumber() { return 5; }
}
