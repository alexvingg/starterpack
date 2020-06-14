package br.com.starterpack.service;

import br.com.starterpack.core.repository.IRepository;
import br.com.starterpack.core.service.ICrudService;
import br.com.starterpack.entity.QUser;
import br.com.starterpack.entity.User;
import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements ICrudService<User, String>, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public IRepository getRepository() {
        return userRepository;
    }

    @Override
    public void applyFilters(Map<String, String> filters, BooleanBuilder predicate) {
        if(filters.containsKey("notUsers")){
            String[] ids = filters.get("notUsers").split(",");
            predicate.and(QUser.user.id.notIn(ids));
            filters.remove("notUsers");
        }
    }

    @Override
    public User mergeToUpdate(User userUpdated, User object) {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                this.getGrantedAuthorities(user.getRoles()));
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    }
}
