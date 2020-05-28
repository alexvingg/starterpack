package br.com.starterpack.core.resource;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.service.ICrudService;

public interface ICrudResource<T extends AbstractEntity, I extends ICrudService<T, S>, S> extends
        IResourceGet<T,I,S>, IResourceGetAll<T,I,S>, IResourceSave<T,I,S>,
        IResourceDelete<T, I, S>, IResourceUpdate<T,I,S> {

}
