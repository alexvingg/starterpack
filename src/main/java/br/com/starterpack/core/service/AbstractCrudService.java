package br.com.starterpack.core.service;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.repository.BaseRepository;
import br.com.starterpack.core.response.PaginateResponse;
import br.com.starterpack.core.util.DinamicFilterUtil;
import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.exception.ModelNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractCrudService<T extends AbstractEntity, S> implements ICrudService<T, S> {

    public abstract BaseRepository<T, S> getRepository();
    public abstract T mergeToUpdate(T objectUpdated, T object);

    @Override
    public T get(S id) {
        this.beforeGet(id);
        T entity = this.getRepository().findById(id).orElseThrow(() -> new ModelNotFoundException());
        this.afterGet(entity);
        return entity;
    }

    public void beforeGet(S id){
    }

    public void afterGet(T entity){
    }

    @Override
    public PaginateResponse get(int page, int size, String order, String orderBy,
                                      Integer limit, Map<String, String> filters) {
        this.beforeGetAll(filters, page, size, order, orderBy, limit);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(order));

        if(!orderBy.isEmpty()){
            pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy));
        }

        if(limit != 0){
            pageRequest = PageRequest.of(0, limit);
        }

        Query query = new Query().with(pageRequest);

        List<CriteriaDefinition> criterias = this.applyFilters(filters);

        filters.entrySet().stream()
            .forEach((entry) -> {
                String value = entry.getValue();
                String[] dsl = entry.getKey().split("\\|");
                String attributePath = dsl[0];
                String operator = (dsl.length >= 2) ? dsl[1] : "eq";
                String attributeType =  (dsl.length == 3) ? dsl[2] : "string";

                try {
                    CriteriaDefinition criteria = DinamicFilterUtil.getDefinedCriteria(operator, attributeType, attributePath, value);
                    query.addCriteria(criteria);

                } catch(NumberFormatException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new BusinessException("DYNAMIC_FILTER_CONVERT", e);
                };
        });

        criterias.forEach(criteria -> query.addCriteria(criteria));

        Page<T> pageResponse = this.getRepository().findAll(query, pageRequest);

        this.afterGetAll(pageResponse.getContent());

        return PaginateResponse.builder().items(pageResponse.getContent()).total(pageResponse.getTotalElements()).build();
    }

    public void beforeGetAll(Map<String, String> filters, int page,
                             int size, String order,
                             String orderBy, Integer limit){
    }

    public List<CriteriaDefinition> applyFilters(Map<String, String> filters){
        return new ArrayList<>();
    }

    public void afterGetAll(List<T> all){

    }

    @Override
    public T save(T entity) {
        this.beforeSave(entity);
        entity = (T) this.getRepository().save(entity);
        this.afterSave(entity);
        return entity;
    }

    public void beforeSave(T object){
    }

    public void afterSave(T object){
    }

    @Override
    public T update(S id, T entity) {
        T objectUpdate = this.getRepository().findById(id).orElseThrow(() -> new ModelNotFoundException());
        this.beforeUpdate(entity, objectUpdate);
        objectUpdate = this.mergeToUpdate(objectUpdate, entity);
        this.getRepository().save(objectUpdate);
        this.afterUpdate(entity, objectUpdate);
        return entity;
    }

    public void beforeUpdate(T objectUpdated, T object){
    }

    public void afterUpdate(T objectUpdated, T object){
    }

    @Override
    public void delete(S id) {
        this.beforeDelete(id);
        this.getRepository().deleteById(id);
        this.afterDelete(id);
    }

    public void beforeDelete(S id) {
    }

    public void afterDelete(S id){
    }
}
