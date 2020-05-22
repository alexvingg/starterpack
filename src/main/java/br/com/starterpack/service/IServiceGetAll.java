package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IServiceGetAll<T extends AbstractModel, S> extends IService<IRepository<T, S>> {

    default void beforeGetAll(int page, int size, String order, String orderBy){

    }

    default void afterGetAll(List<T> all){

    }

    default Page getAll(int page, int size, String order, String orderBy) {
        beforeGetAll(page, size, order, orderBy);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(order));

        if(!orderBy.isEmpty()){
            pageRequest = PageRequest.of(page, size, Sort.by(order, orderBy));
        }

        Page all = this.getRepository().findAll(pageRequest);
        afterGetAll(all.getContent());
        return all;
    }

}
