package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;

public interface IServiceAbstract<T extends AbstractModel, S extends  String> extends IServiceSave<T>,
        IServiceDelete<T>, IServiceShow<T, S>, IServiceUpdate<T, S>, IServiceIndex<T> {
}
