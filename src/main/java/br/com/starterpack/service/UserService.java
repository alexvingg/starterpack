package br.com.starterpack.service;

import br.com.starterpack.model.User;
import br.com.starterpack.repository.IRepository;
import br.com.starterpack.repository.UserRepository;
import br.com.starterpack.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements IServiceAbstract<User, String> {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public IRepository getRepository() {
        return userRepository;
    }

    @Override
    public void beforeSave(User object) {
        object.setPassword(passwordEncoder.encode("123456"));
    }

    @Override
    public User beforeUpdate(String id, User object) {
        User userUpdated = this.userRepository.findById(object.getId()).orElseThrow();

        userUpdated.setUsername(object.getUsername());
        userUpdated.setEmail(object.getEmail());
        userUpdated.setRoles(object.getRoles());

        return userUpdated;
    }

    public User updateProfile(User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!user.getUsername().equals(authentication.getName())){
            new RuntimeException();
        }

        User userUpdated = this.userRepository.findById(user.getId()).orElseThrow();

        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            if(!user.getPassword().equals(user.getConfirmPassword())){
                new RuntimeException();
            }
            userUpdated.setPassword(passwordEncoder.encode(user.getPassword()));
        }


        userUpdated.setUsername(user.getUsername());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setImage(user.getImage());
        return this.userRepository.save(userUpdated);
    }
}
