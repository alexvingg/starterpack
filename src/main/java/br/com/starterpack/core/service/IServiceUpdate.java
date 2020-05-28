package br.com.starterpack.core.service;

import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.repository.IRepository;

public interface IServiceUpdate<T extends AbstractEntity, S> extends IService<IRepository<T, S>> {

    default void beforeUpdate(T objectUpdated, T object){
    }

    default void afterUpdate(T objectUpdated, T object){
    }

    T mergeToUpdate(T objectUpdated, T object);

    default T update(S id, T object) {
        T objectUpdate = this.getRepository().findById(id).orElseThrow(() -> new BusinessException("ID_NOT_EXIST"));
        this.beforeUpdate(object, objectUpdate);
        objectUpdate = this.mergeToUpdate(objectUpdate, object);
        this.getRepository().save(objectUpdate);
        this.afterUpdate(object, objectUpdate);
        return object;
    }

}
