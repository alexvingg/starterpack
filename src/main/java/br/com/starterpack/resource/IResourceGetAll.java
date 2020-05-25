package br.com.starterpack.resource;

import br.com.starterpack.enums.RequestParamEnum;
import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.service.IServiceAbstract;
import br.com.starterpack.util.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

public interface IResourceGetAll<T extends AbstractModel, I extends IServiceAbstract<T, S>, S> extends IResource<I> {

    default Response preResponseGetAll(Page<T> all, Response response, int page){
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

        allRequestParam.keySet().removeAll(RequestParamEnum.getValues());
        Page<T> all = this.getService().getAll(page, perPage, orderType, orderBy, limit, allRequestParam);

        Response response = Response.ok();
        response.addData("total", all.getTotalElements());
        response.addData("page", page);
        response.addData("items", all.getContent());

        response = this.preResponseGetAll(all, response, page);

        dr.setResult(ResponseEntity.ok().body(response));

        return dr;
    }

}
