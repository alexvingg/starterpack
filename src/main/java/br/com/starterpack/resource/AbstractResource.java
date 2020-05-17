package br.com.starterpack.resource;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.service.IServiceAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractResource<T extends AbstractModel, I extends IServiceAbstract, S> implements IResource<I> {

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<?>> getAll() {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

        log.info("getAll {}", this.getClass().getCanonicalName());

        List<T> all = this.getService().index();

        dr.setResult(ResponseEntity.ok(all));
        return dr;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<?>> get(@PathVariable S id) {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

        log.info("get by id {} , {}", id, this.getClass().getCanonicalName());

        Optional object = this.getService().show(id);

        dr.setResult(ResponseEntity.ok(object.orElseThrow()));
        return dr;
    }

    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<?>> save(@RequestBody T object) {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

        log.info("save {}, {}", object, this.getClass().getCanonicalName());

        object = (T) this.getService().save(object);

        dr.setResult(ResponseEntity.ok(object));
        return dr;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<?>> edit(@PathVariable S id, @RequestBody T object) {
        final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

        log.info("edit id {} obj {}, {}", id, object, this.getClass().getCanonicalName());

        object = (T) this.getService().update(id, object);

        dr.setResult(ResponseEntity.ok(object));
        return dr;
    }
}
