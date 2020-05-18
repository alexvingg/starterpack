package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.IRepository;

public interface IServiceUpdate<T extends AbstractModel, S> extends IService<IRepository<T, S>> {

    default void beforeUpdate(S id, T object){

    }

    default void afterUpdate(S id, T object){

    }

    default T update(S id, T object) {
        this.beforeUpdate(id ,object);
        object = this.getRepository().save(object);
        this.afterUpdate(id, object);
        return object;
    }

}
