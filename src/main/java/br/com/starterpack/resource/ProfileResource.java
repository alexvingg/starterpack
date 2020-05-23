package br.com.starterpack.resource;

import br.com.starterpack.model.User;
import br.com.starterpack.response.UserResponse;
import br.com.starterpack.service.UserService;
import br.com.starterpack.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/profile")
public class ProfileResource {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<Response>> update(@Valid @RequestBody User user) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();
        user = this.userService.updateProfile(user);
        dr.setResult(ResponseEntity.ok().body(Response.ok().addData("data", UserResponse.toJson(user))));
        return dr;
    }
}
