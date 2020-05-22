package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.IRepository;

public interface IServiceUpdate<T extends AbstractModel, S> extends IService<IRepository<T, S>> {

    default T beforeUpdate(S id, T object){
        return object;
    }

    default T afterUpdate(S id, T object){
        return object;
    }

    default T update(S id, T object) {
        object = this.beforeUpdate(id ,object);
        object = this.getRepository().save(object);
        object = this.afterUpdate(id, object);
        return object;
    }

}
