package br.com.starterpack.core.service;

import br.com.starterpack.core.entity.AbstractEntity;

public interface ICrudService<T extends AbstractEntity, S> extends IServiceSave<T, S>,
        IServiceDelete<T, S>, IServiceGet<T, S>, IServiceUpdate<T, S>, IServiceGetAll<T, S> {
}
