package br.com.starterpack.resource;

import br.com.starterpack.entity.User;
import br.com.starterpack.response.UserResponse;
import br.com.starterpack.service.UserService;
import br.com.starterpack.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserResource implements ICrudResource<User, UserService, String> {

    @Autowired
    private UserService userService;

    @Override
    public UserService getService() {
        return this.userService;
    }

    @Override
    public Response preResponseGetAll(Page<User> all, Response response, int page) {
        response = Response.ok();
        response.addData("total", all.getTotalElements());
        response.addData("page", page);
        response.addData("items", UserResponse.toJson(all.getContent()));
        return response;
    }
}
