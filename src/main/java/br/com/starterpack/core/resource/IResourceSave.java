package br.com.starterpack.core.resource;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.service.ICrudService;
import br.com.starterpack.core.response.Response;
import br.com.starterpack.core.validation.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

public interface IResourceSave<T extends AbstractEntity, I extends ICrudService<T, S>, S> extends IResource<I>{

    @RequestMapping(method = RequestMethod.POST)
    default DeferredResult<ResponseEntity<Response>> save(@Validated({OnCreate.class}) @RequestBody T object) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        object = this.getService().save(object);

        dr.setResult(ResponseEntity.ok(Response.ok().addData("data", object)));
        return dr;
    }
}
