package br.com.starterpack.service;

import br.com.starterpack.entity.User;
import br.com.starterpack.enums.RoleEnum;
import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
