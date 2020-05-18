package br.com.starterpack.resource;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.service.IServiceAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
public abstract class AbstractResource<T extends AbstractModel, I extends IServiceAbstract<T, S>, S> implements IResource<I> {

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<?>> getAll(@RequestParam(value = "page",
            required = false,
            defaultValue = "0") int page, @RequestParam(
            value = "size",
            required = false,
            defaultValue = "10") int size,@RequestParam(
            value = "sort",
            required = false,
            defaultValue = "asc") String sort) {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

        log.info("getAll {}", this.getClass().getCanonicalName());

        Page<T> all = this.getService().getAll(page, size, sort);

        dr.setResult(ResponseEntity.ok(all));
        return dr;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<?>> get(@PathVariable S id) {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

        log.info("get by id {} , {}", id, this.getClass().getCanonicalName());

        T object = this.getService().get(id);

        dr.setResult(ResponseEntity.ok(object));
        return dr;
    }

    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<?>> save(@RequestBody T object) {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

        log.info("save {}, {}", object, this.getClass().getCanonicalName());

        object = this.getService().save(object);

        dr.setResult(ResponseEntity.ok(object));
        return dr;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<?>> edit(@PathVariable S id, @RequestBody T object) {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

        log.info("edit id {} obj {}, {}", id, object, this.getClass().getCanonicalName());

        object = this.getService().update(id, object);

        dr.setResult(ResponseEntity.ok(object));
        return dr;
    }
}
