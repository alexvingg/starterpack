package br.com.starterpack.repository;

import br.com.starterpack.core.repository.BaseRepository;
import br.com.starterpack.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

    User findByUsername(String name);
}
