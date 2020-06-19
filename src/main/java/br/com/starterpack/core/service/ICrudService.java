package br.com.starterpack.core.service;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.response.PaginateResponse;

import java.util.Map;

public interface ICrudService<T, S> {
    T get(S id);
    PaginateResponse get(int page, int size, String order, String orderBy,
                               Integer limit, Map<String, String> filters);
    T save(T entity);
    T update(S id, T entity);
    void delete(S id);
}
