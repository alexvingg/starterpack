package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.RepositoryInterface;

import java.util.List;

public interface IServiceIndex<T extends AbstractModel> extends IService<RepositoryInterface> {

    default void beforeIndex(){

    }

    default void afterIndex(List<T> all){

    }

    default List<T> index() {
        beforeIndex();
        List<T> all = this.getRepository().findAll();
        afterIndex(all);
        return all;
    }

}
