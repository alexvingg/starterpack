package br.com.starterpack.resource;

import br.com.starterpack.entity.AbstractEntity;
import br.com.starterpack.service.ICrudService;

public interface ICrudResource<T extends AbstractEntity, I extends ICrudService<T, S>, S> extends
        IResourceGet<T,I,S>, IResourceGetAll<T,I,S>, IResourceSave<T,I,S>,
        IResourceDelete<T, I, S>, IResourceUpdate<T,I,S> {

}
