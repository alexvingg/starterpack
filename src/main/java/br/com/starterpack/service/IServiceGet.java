package br.com.starterpack.service;

import br.com.starterpack.entity.AbstractEntity;
import br.com.starterpack.repository.IRepository;

public interface IServiceGet<T extends AbstractEntity, S> extends IService<IRepository<T, S>> {

    default void beforeGet(S id){

    }

    default void afterGet(T object){

    }

    default T get(S id) {
        beforeGet(id);
        T object = this.getRepository().findById(id).orElse(null);
        afterGet(object);
        return object;
    }

}
