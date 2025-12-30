package lutz.algasensors.seeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SeederApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeederApplication.class, args);
	}
}

