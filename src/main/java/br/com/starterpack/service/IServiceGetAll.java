package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.IRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface IServiceGetAll<T extends AbstractModel, S> extends IService<IRepository<T, S>> {

    default void beforeGetAll(Map<String, String> filters, BooleanBuilder predicate, PageRequest pageRequest){

    }

    default void afterGetAll(List<T> all){

    }

    default Page getAll(int page, int size, String order, String orderBy, Integer limit, Map<String, String> filters) {
        page = page == 0 ? page : page  - 1;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(order));

        if(!orderBy.isEmpty()){
            pageRequest = PageRequest.of(page, size, Sort.by(order, orderBy));
        }

        if(limit != 0){
            pageRequest = PageRequest.of(0, limit);
        }

        PathBuilder<T> pathBuilder = new PathBuilder(AbstractModel.class, "");
        BooleanBuilder predicate = new BooleanBuilder();

        this.beforeGetAll(filters, predicate, pageRequest);

        filters.forEach((o, o2) -> {
            StringPath usernamePath = pathBuilder.getString(o);
            BooleanExpression booleanExpression = usernamePath.containsIgnoreCase(o2);
            predicate.and(booleanExpression);
        });

        Page all = this.getRepository().findAll(predicate, pageRequest);

        this.afterGetAll(all.getContent());
        return all;
    }


}
