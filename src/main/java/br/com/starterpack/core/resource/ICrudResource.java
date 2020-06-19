package br.com.starterpack.core.resource;

import br.com.starterpack.core.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

public interface ICrudResource<T, S> {

    DeferredResult<ResponseEntity<Response>> get(S id);

    DeferredResult<ResponseEntity<Response>> get(int page, int perPage, String orderType, String orderBy,
                                                           int limit,Map<String,String> allRequestParam);
    DeferredResult<ResponseEntity<Response>> save(T object);

    DeferredResult<ResponseEntity<Response>> update(S id, T object);

    DeferredResult<ResponseEntity<Response>> delete(S id);
}
