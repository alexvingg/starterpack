package br.com.starterpack.core.repository;

import br.com.starterpack.core.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractEntity, ID> extends MongoRepository<T, ID> {
    Page<T> findAll(Query query, Pageable page);
}