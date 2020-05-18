package br.com.starterpack.service;

import br.com.starterpack.model.AbstractModel;

public interface IServiceAbstract<T extends AbstractModel, S> extends IServiceSave<T, S>,
        IServiceDelete<T, S>, IServiceGet<T, S>, IServiceUpdate<T, S>, IServiceGetAll<T, S> {
}
