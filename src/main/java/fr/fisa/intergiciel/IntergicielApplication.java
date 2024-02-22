package fr.fisa.intergiciel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication(scanBasePackages = {"fr.fisa.intergiciel"})
public class IntergicielApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntergicielApplication.class, args);
	}

}
