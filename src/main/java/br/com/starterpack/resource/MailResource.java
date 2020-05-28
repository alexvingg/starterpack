package br.com.starterpack.resource;

import br.com.starterpack.mail.MailRequest;
import br.com.starterpack.service.MailService;
import br.com.starterpack.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/api/v1/mails")
public class MailResource {

    @Autowired
    private MailService mailService;

    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<Response>> sendMail(@RequestBody MailRequest mailRequest) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();
        this.mailService.sendMail(mailRequest);
        dr.setResult(ResponseEntity.ok().body(Response.ok()));
        return dr;
    }
}
