package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.IRepository;

public interface IServiceDelete<T extends AbstractModel, S> extends IService<IRepository<T, S>> {

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
