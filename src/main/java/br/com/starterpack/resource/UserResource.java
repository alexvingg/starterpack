package br.com.starterpack.resource;

import br.com.starterpack.core.resource.ICrudResource;
import br.com.starterpack.core.response.GetAllResponse;
import br.com.starterpack.entity.User;
import br.com.starterpack.response.UserResponse;
import br.com.starterpack.service.UserService;
import br.com.starterpack.core.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Response preResponseGetAll(List<User> items, Long total) {
        Response response = Response.ok();
        response.addData("total", total);
        response.addData("items", UserResponse.toJson(items));
        return response;
    }
}
