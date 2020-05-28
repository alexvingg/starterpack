package br.com.starterpack.service;

import br.com.starterpack.entity.AbstractEntity;

public interface ICrudService<T extends AbstractEntity, S> extends IServiceSave<T, S>,
        IServiceDelete<T, S>, IServiceGet<T, S>, IServiceUpdate<T, S>, IServiceGetAll<T, S> {
}
