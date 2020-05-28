package br.com.starterpack.service;

import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.entity.AbstractEntity;
import br.com.starterpack.repository.IRepository;

public interface IServiceUpdate<T extends AbstractEntity, S> extends IService<IRepository<T, S>> {

    default void beforeUpdate(S id, T objectUpdated, T object){
    }

    default void afterUpdate(S id, T objectUpdated, T object){
    }

    default T mergeToUpdate(S id, T objectUpdated, T object) {
        return objectUpdated;
    }

    default T update(S id, T object) {
        T objectUpdate = this.getRepository().findById(id).orElseThrow(() -> new BusinessException("ID_NOT_EXIST"));
        objectUpdate = this.mergeToUpdate(id, objectUpdate, object);
        this.beforeUpdate(id, object, objectUpdate);
        this.getRepository().save(objectUpdate);
        this.afterUpdate(id, object, objectUpdate);
        return object;
    }

}
