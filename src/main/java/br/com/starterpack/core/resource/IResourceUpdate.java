package br.com.starterpack.core.resource;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.service.ICrudService;
import br.com.starterpack.core.response.Response;
import br.com.starterpack.core.validation.OnUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

public interface IResourceUpdate <T extends AbstractEntity, I extends ICrudService<T, S>, S> extends IResource<I> {

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    default DeferredResult<ResponseEntity<Response>> update(@PathVariable S id,
                                                            @Validated({OnUpdate.class}) @RequestBody T object) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        object = this.getService().update(id, object);

        dr.setResult(ResponseEntity.ok(Response.ok().addData("data", object)));
        return dr;
    }

}
