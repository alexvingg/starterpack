package br.com.starterpack.repository;

import br.com.starterpack.core.repository.IRepository;
import br.com.starterpack.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends IRepository<User, String> {

    User findByUsername(String name);
}
