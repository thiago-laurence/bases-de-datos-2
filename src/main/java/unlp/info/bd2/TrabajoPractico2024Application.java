package unlp.info.bd2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrabajoPractico2024Application {

	public static void main(String[] args) {
		SpringApplication.run(TrabajoPractico2024Application.class, args);
	}

}
