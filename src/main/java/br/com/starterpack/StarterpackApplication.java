package br.com.starterpack;

import br.com.starterpack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class StarterpackApplication {

	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StarterpackApplication.class, args);
	}
}
