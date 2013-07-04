package org.openinfinity.tagcloud.domain.service;

import java.math.BigInteger;
import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Entity;
import org.openinfinity.tagcloud.domain.entity.Tag;

public abstract interface AbstractCrudServiceInterface<T extends Entity> {

	public T create(T entity);
	
	public void update(T entity);
	
	public Collection<T> loadAll();
	
	public T loadById(String id);
	
	public void delete (T entity);
	
	public static final String UNIQUE_EXCEPTION_ENTITY_ALREADY_EXISTS = "localized.exception.entity.already.exists";

	public static final String UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST = "localized.exception.entity.does.not.exist";

	public boolean contains(T entity);

}
