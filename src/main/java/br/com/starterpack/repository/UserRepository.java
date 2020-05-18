package br.com.starterpack.repository;

import br.com.starterpack.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends IRepository<User, String> {
}
