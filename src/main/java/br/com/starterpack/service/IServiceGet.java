package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.repository.RepositoryInterface;

import java.util.Optional;

public interface IServiceShow<T extends AbstractModel, S> extends IService<RepositoryInterface> {

    default void beforeShow(S id){

    }

    default void afterShow(Optional object){

    }

    default Optional show(S id) {
        beforeShow(id);
        Optional object = this.getRepository().findById(id);
        afterShow(object);
        return object;
    }

}
