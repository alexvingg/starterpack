package br.com.starterpack.service;

import br.com.starterpack.entity.AbstractEntity;
import br.com.starterpack.repository.IRepository;

public interface IServiceSave<T extends AbstractEntity, S> extends IService<IRepository<T, S>> {

    default void beforeSave(T object){

    }

    default void afterSave(T object){

    }

    default T save(T object) {
        beforeSave(object);
        object = (T) this.getRepository().save(object);
        afterSave(object);
        return object;
    }

}
