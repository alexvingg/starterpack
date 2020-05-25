package br.com.starterpack.service;

import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.model.User;
import br.com.starterpack.repository.IRepository;
import br.com.starterpack.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    public void beforeGetAll(Map<String, String> filters, BooleanBuilder predicate, PathBuilder pathBuilder, PageRequest pageRequest) {

        if(filters.containsKey("notUsers")){
            String[] ids = filters.get("notUsers").split(",");
            StringPath idPath = pathBuilder.getString("id");
            BooleanExpression booleanExpression = idPath.notIn(ids);
            predicate.and(booleanExpression);
            filters.remove("notUsers");
        }

    }

    @Override
    public User mergeToUpdate(String id, User userUpdated, User object) {

        userUpdated.setUsername(object.getUsername());
        userUpdated.setEmail(object.getEmail());
        userUpdated.setRoles(object.getRoles());
        userUpdated.setName(object.getName());
        return userUpdated;
    }

    public User updateProfile(User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User userUpdated = this.userRepository.findByUsername(authentication.getName());

        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            if(!user.getPassword().equals(user.getConfirmPassword())){
                throw new BusinessException("A senha informada é inválida");
            }
            userUpdated.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userUpdated.setUsername(user.getUsername());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setName(user.getName());
        userUpdated.setImage(user.getImage());
        return this.userRepository.save(userUpdated);
    }
}
