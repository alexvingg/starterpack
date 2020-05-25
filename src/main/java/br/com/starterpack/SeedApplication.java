package br.com.starterpack;

import br.com.starterpack.model.User;
import br.com.starterpack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

//@SpringBootApplication
public class SeedApplication implements CommandLineRunner {
    @Autowired
    private UserRepository repository;

    public void run(String... args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        repository.deleteAll();

        // save a couple of customers
        repository.save(new User("alex","alex", "alexvingg@gmail.com", passwordEncoder.encode("123456"),
                null, null, Arrays.asList("ADMIN")));
        repository.save(new User("alex", "roque", "roquealmeidacosta@gmail.com", passwordEncoder.encode("123456"),
                null, null, Arrays.asList("USER")));

        // fetch all customers
        System.out.println("Users found with findAll():");
        System.out.println("-------------------------------");
        for (User user : repository.findAll()) {
            System.out.println(user);
        }
        System.out.println();

        System.out.println("User found with findByUsername('thiago'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByUsername("thiago"));
    }

}
