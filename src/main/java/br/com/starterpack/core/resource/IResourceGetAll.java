package br.com.starterpack.core.resource;

import br.com.starterpack.core.response.GetAllResponse;
import br.com.starterpack.enums.RequestParamEnum;
import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.service.ICrudService;
import br.com.starterpack.core.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

public interface IResourceGetAll<T extends AbstractEntity, I extends ICrudService<T, S>, S> extends IResource<I> {

    default Response preResponseGetAll(List<T> items, Long total){
        Response response = Response.ok();
        response.addData("total", total);
        response.addData("items", items);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    default DeferredResult<ResponseEntity<Response>> getAll(@RequestParam(value = "page",
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

        GetAllResponse all = this.getService().getAll(page, perPage, orderType, orderBy, limit, allRequestParam);
        Response response = this.preResponseGetAll(all.getItems(), all.getTotal());
        dr.setResult(ResponseEntity.ok().body(response));

        return dr;
    }

}
