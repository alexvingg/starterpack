package br.com.starterpack.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/api/v1/home")
public class HomeController {
    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<?>> get() {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();
        dr.setResult(ResponseEntity.ok("ok"));
        return dr;
    }

}
