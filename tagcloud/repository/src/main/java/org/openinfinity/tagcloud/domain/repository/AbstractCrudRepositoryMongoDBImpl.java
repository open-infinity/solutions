package org.openinfinity.tagcloud.domain.repository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Generic implementation for crud methods 
 * 
 * @author Joosa Kurvinen
 */
public abstract class AbstractCrudRepositoryMongoDBImpl<T, IDTYPE> implements AbstractCrudRepositoryInterface<T, IDTYPE> {
	@Autowired
	MongoTemplate mongoTemplate;

    @Override
    public T create(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    @Override
    public void update(final T entity) {
		mongoTemplate.save(entity);
    }

    @Override
    public Collection<T> loadAll() {
        return mongoTemplate.findAll(getGenericClassType());
    }

    @Override
    public T loadById(IDTYPE id) {
        return mongoTemplate.findById(id, getGenericClassType());
    }

    @Override
    public void delete(T entity) {
        mongoTemplate.remove(entity);
    }
    
	@Override
	public Collection<T> loadByText(String text) {
		Query query = new Query(Criteria.where("text").is(text));
		return mongoTemplate.find(query, getGenericClassType());
	}

	@Override
	public void dropCollection() {
		mongoTemplate.dropCollection(getGenericClassType());
	}
	
	private Class<T> getGenericClassType(){
        return GenericTypeResolver.resolveTypeArguments(getClass(), AbstractCrudRepositoryMongoDBImpl.class)[0];
    }
}
