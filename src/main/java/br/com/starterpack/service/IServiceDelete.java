package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.RepositoryInterface;

public interface IServiceDelete<T extends AbstractModel> extends IService<RepositoryInterface> {

    default void beforeDelete(T object) {
    }

    default void afterDelete(T object){
    }

    default T delete(T object) {
        this.beforeDelete(object);
        this.getRepository().delete(object);
        this.afterDelete(object);
        return object;
    }

}
