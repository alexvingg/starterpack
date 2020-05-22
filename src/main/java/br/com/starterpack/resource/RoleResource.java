package br.com.starterpack.resource;

import br.com.starterpack.enums.RoleEnum;
import br.com.starterpack.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/api/v1/roles")
public class RoleResource {

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Response>> getAll() {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();
        dr.setResult(ResponseEntity.ok().body(Response.ok().addData("items", RoleEnum.getValue())));
        return dr;
    }
}
