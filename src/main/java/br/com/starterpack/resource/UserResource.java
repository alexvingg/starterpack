package br.com.starterpack.resource;

import br.com.starterpack.model.User;
import br.com.starterpack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/user")
public class UserResource extends AbstractResource<User, UserService, String> {

    @Autowired
    private UserService userService;

    @Override
    public UserService getService() {
        return this.userService;
    }
}
