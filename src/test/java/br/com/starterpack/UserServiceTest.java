package br.com.starterpack;

import br.com.starterpack.model.User;
import br.com.starterpack.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private static User user;

    @BeforeAll
    public static void createUser(){
        user = new User();
        user.setName("Alex");
        user.setEmail("alexvingg@gmail.com");
        user.setPassword("123456");
    }

    @Test
    void testSave() {
        User user = this.userService.save(this.user);
        assertEquals(user.getName(), this.user.getName());
        assertNotNull(this.user.getId());
    }

    @Test
    void testDelete() {
        User user = this.userService.delete(this.user);
        assertEquals(user.getName(), this.user.getName());
    }

    @Test
    void testShow() {
        User user = this.userService.save(this.user);
        String id = user.getId();
        User userShow = this.userService.get(id);
        assertEquals(userShow.getId(), id);
    }

    @Test
    void testUpdate() {
        User user = this.userService.save(this.user);
        String id = user.getId();
        User userShow = this.userService.get(id);

        userShow.setName("Alex Update");

        var userUpdateNew = this.userService.update(userShow.getId(), userShow);

        assertEquals(userShow.getName(), userUpdateNew.getName());
    }
}
