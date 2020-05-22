package br.com.starterpack.service;

import br.com.starterpack.model.User;
import br.com.starterpack.repository.IRepository;
import br.com.starterpack.repository.UserRepository;
import br.com.starterpack.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements IServiceAbstract<User, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public IRepository getRepository() {
        return userRepository;
    }

}
