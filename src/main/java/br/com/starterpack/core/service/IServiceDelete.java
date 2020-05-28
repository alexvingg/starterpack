package br.com.starterpack.core.service;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.repository.IRepository;

public interface IServiceDelete<T extends AbstractEntity, S> extends IService<IRepository<T, S>> {

    default void beforeDelete(S id) {
    }

    default void afterDelete(S id){
    }

    default void delete(S id) {
        this.beforeDelete(id);
        this.getRepository().deleteById(id);
        this.afterDelete(id);
    }

}
