package br.com.starterpack.core.service;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.repository.IRepository;

public interface IServiceSave<T extends AbstractEntity, S> extends IService<IRepository<T, S>> {

    default void beforeSave(T object){

    }

    default void afterSave(T object){

    }

    default T save(T object) {
        this.beforeSave(object);
        object = (T) this.getRepository().save(object);
        this.afterSave(object);
        return object;
    }

}
