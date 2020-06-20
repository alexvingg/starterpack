package br.com.starterpack.core.repository;

import br.com.starterpack.core.entity.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.io.Serializable;
import java.util.List;

public class BaseRepositoryImpl<T extends AbstractEntity, ID extends Serializable> extends SimpleMongoRepository<T, ID>
        implements BaseRepository<T, ID> {

    protected MongoOperations operations;
    protected MongoEntityInformation<T, ID> entityInformation;

    @Autowired
    public BaseRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations operations) {
        super(entityInformation, operations);
        this.operations = operations;
        this.entityInformation = entityInformation;
    }

    @Override
    public Page<T> findAll(Query query, Pageable page) {
        List<T> results = (List<T>) operations.find(query.with(page), entityInformation.getJavaType(), entityInformation.getCollectionName());
        return (Page<T>) PageableExecutionUtils.getPage(
                results,
                page,
                () -> operations.count(Query.of(query).limit(-1).skip(-1), entityInformation.getJavaType(), entityInformation.getCollectionName()));
    }
}
