package br.com.starterpack.core.service;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.repository.IRepository;
import br.com.starterpack.core.response.PaginateResponse;
import br.com.starterpack.exception.ModelNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public abstract class AbstractCrudService<T extends AbstractEntity, S> implements ICrudService<T, S> {

    public abstract IRepository<T, S> getRepository();
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

        PathBuilder<T> pathBuilder = new PathBuilder(AbstractEntity.class, "");
        BooleanBuilder predicate = new BooleanBuilder();

        this.applyFilters(filters, predicate);

        filters.forEach((key, value) -> {
            if(value.equals("true") || value.equals("false")){
                BooleanPath booleanPath = pathBuilder.getBoolean(key);
                BooleanExpression booleanExpression = booleanPath.eq(Boolean.valueOf(value));
                predicate.and(booleanExpression);
            }else if(StringUtils.isNumeric(value)){
                NumberPath numberPath = pathBuilder.getNumber(key, Integer.class);
                BooleanExpression booleanExpression = numberPath.eq(Integer.valueOf(value));
                predicate.and(booleanExpression);
            }else{
                StringPath stringPath = pathBuilder.getString(key);
                BooleanExpression booleanExpression = stringPath.eq(value);
                predicate.and(booleanExpression);
            }
        });

        Page all = this.getRepository().findAll(predicate, pageRequest);

        this.afterGetAll(all.getContent());

        return PaginateResponse.builder()
                .items(all.getContent())
                .total(all.getTotalElements()).build();
    }

    public void beforeGetAll(Map<String, String> filters, int page,
                             int size, String order,
                             String orderBy, Integer limit){
    }

    public void applyFilters(Map<String, String> filters, BooleanBuilder predicate){
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
