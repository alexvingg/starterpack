package br.com.starterpack.resource;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.service.IServiceAbstract;
import br.com.starterpack.util.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

public interface ICrudResource<T extends AbstractModel, I extends IServiceAbstract<T, S>, S> extends IResource<I> {

    @RequestMapping(method = RequestMethod.GET)
    default DeferredResult<ResponseEntity<Response>> getAll(@RequestParam(value = "page",
            required = false,
            defaultValue = "0") int page, @RequestParam(
            value = "size",
            required = false,
            defaultValue = "10") int size,@RequestParam(
            value = "sort",
            required = false,
            defaultValue = "asc") String sort) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        Page<T> all = this.getService().getAll(page, size, sort);

        Response response = Response.ok();
        response.addData("total", all.getTotalPages());
        response.addData("page", page);
        response.addData("data", all.getContent());

        dr.setResult(ResponseEntity.ok().body(response));
        return dr;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    default DeferredResult<ResponseEntity<Response>> get(@PathVariable S id) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();
        T object = this.getService().get(id);

        dr.setResult(ResponseEntity.ok().body(Response.ok().addData("data", object)));
        return dr;
    }

    @RequestMapping(method = RequestMethod.POST)
    default DeferredResult<ResponseEntity<Response>> save(@RequestBody T object) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        object = this.getService().save(object);

        dr.setResult(ResponseEntity.ok(Response.ok().addData("data", object)));
        return dr;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    default DeferredResult<ResponseEntity<Response>> edit(@PathVariable S id, @RequestBody T object) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        object = this.getService().update(id, object);

        dr.setResult(ResponseEntity.ok(Response.ok().addData("data", object)));
        return dr;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    default DeferredResult<ResponseEntity<Response>> delete(@PathVariable S id) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        this.getService().delete(id);
        dr.setResult(ResponseEntity.ok(Response.ok()));
        return dr;
    }
}
