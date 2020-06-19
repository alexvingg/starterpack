package br.com.starterpack.core.resource;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.response.PaginateResponse;
import br.com.starterpack.core.response.Response;
import br.com.starterpack.core.service.ICrudService;
import br.com.starterpack.core.validation.OnCreate;
import br.com.starterpack.core.validation.OnUpdate;
import br.com.starterpack.enums.RequestParamEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCrudResource<T extends AbstractEntity, I extends ICrudService<T, S>, S>
        implements ICrudResource<T,S> {

    public abstract I getService();

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Response>> get(@PathVariable S id) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();
        T object = this.getService().get(id);

        dr.setResult(ResponseEntity.ok().body(Response.ok().addData("data", object)));
        return dr;
    }

    public Response preResponseGetAll(List<T> items, Long total){
        Response response = Response.ok();
        response.addData("total", total);
        response.addData("items", items);
        return response;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Response>> get(@RequestParam(value = "page",
            required = false,
            defaultValue = "0") int page, @RequestParam(
            value = "perPage",
            required = false,
            defaultValue = "10") int perPage, @RequestParam(
            value = "orderType",
            required = false,
            defaultValue = "ASC") String orderType, @RequestParam(
            value = "orderBy",
            required = false,
            defaultValue = "") String orderBy, @RequestParam(
            value = "limit",
            required = false,
            defaultValue = "0") int limit, @RequestParam
                                                                    Map<String,String> allRequestParam) {

        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        if(allRequestParam == null){
            allRequestParam = new HashMap<>();
        }

        allRequestParam.keySet().removeAll(RequestParamEnum.getValues());
        allRequestParam = allRequestParam.entrySet().stream().filter(s -> !s.getValue().isEmpty())
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        page = page == 0 ? page : page  - 1;

        PaginateResponse all = this.getService().get(page, perPage, orderType, orderBy, limit, allRequestParam);
        Response response = this.preResponseGetAll(all.getItems(), all.getTotal());
        dr.setResult(ResponseEntity.ok().body(response));

        return dr;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<Response>> save(@Validated({OnCreate.class}) @RequestBody T object) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        object = (T) this.getService().save(object);

        dr.setResult(ResponseEntity.ok(Response.ok().addData("data", object)));
        return dr;
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<Response>> update(@PathVariable S id,
                                                            @Validated({OnUpdate.class}) @RequestBody T object) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        object = this.getService().update(id, object);

        dr.setResult(ResponseEntity.ok(Response.ok().addData("data", object)));
        return dr;
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Response>> delete(@PathVariable S id) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        this.getService().delete(id);
        dr.setResult(ResponseEntity.ok(Response.ok()));
        return dr;
    }

}
