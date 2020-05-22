package br.com.starterpack.resource;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.service.IServiceAbstract;
import br.com.starterpack.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

public interface IResourceDelete<T extends AbstractModel, I extends IServiceAbstract<T, S>, S> extends IResource<I> {

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    default DeferredResult<ResponseEntity<Response>> delete(@PathVariable S id) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        this.getService().delete(id);
        dr.setResult(ResponseEntity.ok(Response.ok()));
        return dr;
    }

}
