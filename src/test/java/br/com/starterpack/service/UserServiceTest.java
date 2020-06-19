package br.com.starterpack.service;

import br.com.starterpack.core.response.PaginateResponse;
import br.com.starterpack.entity.QUser;
import br.com.starterpack.entity.User;
import br.com.starterpack.enums.RoleEnum;
import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void retListUser(){

        BooleanBuilder predicate = new BooleanBuilder();
        Page<User> pagedUsers = new PageImpl<>(Arrays.asList());

        Mockito.when(this.userRepository.findAll(predicate,
                PageRequest.of(0, 10, Sort.by("ASC")))).thenReturn(pagedUsers);

        PaginateResponse userAll = this.userService.get(0, 10, "ASC", "", 0,
                new HashMap<>());

        Assert.assertEquals(0, userAll.getItems().size());
    }

    @Test
    public void retListUserFilter(){

        User user = getUser();

        BooleanBuilder predicate = new BooleanBuilder();
        Page<User> pagedUsers = new PageImpl<>(Arrays.asList(user));

        Mockito.when(this.userRepository.findAll(predicate.and(QUser.user.id.notIn("1")),
                PageRequest.of(0, 10, Sort.by("ASC")))).thenReturn(pagedUsers);

        Map filters = new HashMap<>();
        filters.put("notUsers", "1");

        PaginateResponse userAll = this.userService.get(0, 10, "ASC", "", 0,
                filters);

        Assert.assertEquals(1, userAll.getItems().size());
    }

    @Test
    public void saveUser(){

        User user = getUser();

        Mockito.when(this.userRepository.save(user)).thenReturn(user);

        User userSaved = this.userService.save(user);

        Assert.assertEquals(userSaved.getName(), user.getName());
    }

    @Test
    public void updateUser(){

        User user = getUser();

        user.setId("1");

        Mockito.when(this.userRepository.save(user)).thenReturn(user);
        Mockito.when(this.userRepository.findById("1")).thenReturn(Optional.of(user));

        User userUpdated = this.userService.update("1", user);

        Assert.assertEquals(userUpdated.getName(), user.getName());
    }

    @Test
    public void updateProfile(){

        User user = getUser();

        user.setId("1");

        authSecurity(user);
        Mockito.when(this.userRepository.save(user)).thenReturn(user);

        User userUpdated = this.userService.updateProfile(user);

        Assert.assertEquals(userUpdated.getName(), user.getName());
    }

    @Test
    public void updateProfileInvalidPass(){

        User user = getUser();

        user.setId("1");
        user.setConfirmPassword("1234567");

        authSecurity(user);

        BusinessException exception = Assert.assertThrows(BusinessException.class,
                () -> userService.updateProfile(user));

        String expectedMessage = "A senha informada é inválida";
        String actualMessage = exception.getMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void loadUserByName(){
        User user = getUser();

        Mockito.when(this.userRepository.findByUsername(user.getUsername())).thenReturn(user);

        UserDetails userDetails = this.userService.loadUserByUsername(user.getUsername());

        Assert.assertEquals(userDetails.getUsername(), user.getUsername());

    }

    private User getUser() {
        return new User("Alex",
                "alex",
                "alexvingg@gmail.com",
                "123456",
                "123456",
                "",
                Arrays.asList(RoleEnum.ADMIN.getVal()));
    }

    private void authSecurity(User user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(this.userRepository.findByUsername(auth.getName())).thenReturn(user);
    }
}
