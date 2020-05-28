package br.com.starterpack.core.service;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.response.GetAllResponse;
import br.com.starterpack.core.repository.IRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface IServiceGetAll<T extends AbstractEntity, S> extends IService<IRepository<T, S>> {

    default void beforeGetAll(Map<String, String> filters, int page,
                              int size, String order,
                              String orderBy, Integer limit){

    }

    default void applyFilters(Map<String, String> filters, BooleanBuilder predicate){

    }

    default void afterGetAll(List<T> all){

    }

    default GetAllResponse getAll(int page, int size, String order, String orderBy, Integer limit, Map<String, String> filters) {

        this.beforeGetAll(filters, page, size, order, orderBy, limit);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(order));

        if(!orderBy.isEmpty()){
            pageRequest = PageRequest.of(page, size, Sort.by(order, orderBy));
        }

        if(limit != 0){
            pageRequest = PageRequest.of(0, limit);
        }

        PathBuilder<T> pathBuilder = new PathBuilder(AbstractEntity.class, "");
        BooleanBuilder predicate = new BooleanBuilder();

        this.applyFilters(filters, predicate);

        filters.forEach((o, o2) -> {

            if(o2.equals("true") || o2.equals("false")){
                BooleanPath booleanPath = pathBuilder.getBoolean(o);
                BooleanExpression booleanExpression = booleanPath.eq(Boolean.valueOf(o2));
                predicate.and(booleanExpression);
            }else if(StringUtils.isNumeric(o2)){
                NumberPath numberPath = pathBuilder.getNumber(o, Integer.class);
                BooleanExpression booleanExpression = numberPath.eq(Integer.valueOf(o2));
                predicate.and(booleanExpression);
            }else{
                StringPath stringPath = pathBuilder.getString(o);
                BooleanExpression booleanExpression = stringPath.eq(o2);
                predicate.and(booleanExpression);
            }
        });

        Page all = this.getRepository().findAll(predicate, pageRequest);

        this.afterGetAll(all.getContent());

        return GetAllResponse.builder()
                .items(all.getContent())
                .total(all.getTotalElements()).build();
    }


}
