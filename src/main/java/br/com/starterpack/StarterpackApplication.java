package br.com.starterpack;

import br.com.starterpack.core.repository.BaseRepository;
import br.com.starterpack.core.repository.BaseRepositoryFactoryBean;
import br.com.starterpack.core.repository.BaseRepositoryImpl;
import br.com.starterpack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories(
		repositoryBaseClass = BaseRepositoryImpl.class,
		repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class StarterpackApplication {

	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StarterpackApplication.class, args);
	}
}
