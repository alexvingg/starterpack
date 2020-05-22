package br.com.starterpack.resource;

import br.com.starterpack.model.User;
import br.com.starterpack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserResource implements ICrudResource<User, UserService, String> {

    @Autowired
    private UserService userService;

    @Override
    public UserService getService() {
        return this.userService;
    }
}
