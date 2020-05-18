package br.com.starterpack.service;

import br.com.starterpack.model.User;
import br.com.starterpack.repository.IRepository;
import br.com.starterpack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IServiceAbstract<User, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public IRepository getRepository() {
        return userRepository;
    }

}
