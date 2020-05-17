package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.RepositoryInterface;

public interface IServiceUpdate<T extends AbstractModel, S> extends IService<RepositoryInterface> {

    default void beforeUpdate(S id, T object){

    }

    default void afterUpdate(S id, T object){

    }

    default T update(S id, T object) {
        this.beforeUpdate(id ,object);
        object = (T) this.getRepository().save(object);
        this.afterUpdate(id, object);
        return object;
    }

}
