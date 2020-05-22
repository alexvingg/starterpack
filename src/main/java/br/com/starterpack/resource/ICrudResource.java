package br.com.starterpack.resource;

import br.com.starterpack.model.AbstractModel;
import br.com.starterpack.service.IServiceAbstract;

public interface ICrudResource<T extends AbstractModel, I extends IServiceAbstract<T, S>, S> extends
        IResourceGet<T,I,S>, IResourceGetAll<T,I,S>, IResourceSave<T,I,S>,
        IResourceDelete<T, I, S>, IResourceUpdate<T,I,S> {

}
