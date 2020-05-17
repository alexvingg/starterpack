package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.RepositoryInterface;

public interface IServiceSave<T extends AbstractModel> extends IService<RepositoryInterface> {

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
