package org.openinfinity.tagcloud.domain.service;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.exception.ExceptionLevel;
import org.openinfinity.core.util.ExceptionUtil;
import org.openinfinity.tagcloud.domain.entity.Entity;
import org.openinfinity.tagcloud.domain.repository.AbstractCrudRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


public abstract class AbstractCrudServiceImpl<T extends Entity> implements AbstractCrudServiceInterface<T>{

	protected AbstractCrudRepositoryInterface<T> repository;
	protected AbstractSpecification<T> entitySpecification;
	
	@Autowired
	private GenericAutowireService autowireService;
	
	@PostConstruct
	public void genericAutowire() {
		repository = autowireService.resolve(AbstractCrudRepositoryInterface.class, getClass());
		entitySpecification = autowireService.resolve(AbstractSpecification.class, getClass());
	}

	@Log
	@AuditTrail
	@Transactional
	@Override
	public T create(T entity) {
		Collection<T> entities = repository.loadAll();
		if (entitySpecification.isNotEligibleForCreation(entity, entities)) {
			ExceptionUtil.throwApplicationException(
				"Entity already exists: " + entity.getId(), 
				ExceptionLevel.INFORMATIVE, 
				AbstractCrudServiceInterface.UNIQUE_EXCEPTION_ENTITY_ALREADY_EXISTS);
		}
		repository.create(entity);
		return entity;
	}
	
	@Log
	@AuditTrail
	@Transactional
	@Override
	public void update(T entity) {
		if (repository.loadById(entity.getId()) == null) {
			ExceptionUtil.throwBusinessViolationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.ERROR, 
				AbstractCrudServiceInterface.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		repository.update(entity);
	}
	
	@Override
	public Collection<T> loadAll() {
		return repository.loadAll();
	}
	
	@Log
	@AuditTrail
	@Override
	public T loadById(String id) {
		T entity = repository.loadById(id);
		if (entity == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + id, 
				ExceptionLevel.WARNING, 
				AbstractCrudServiceInterface.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		return entity; 
	}
	
	@Log
	@AuditTrail
	@Transactional
	@Override
	public void delete (T entity) {
		if (repository.loadById(entity.getId()) == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.INFORMATIVE, 
				AbstractCrudServiceInterface.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		repository.delete(entity);
	}

	@Log
	@AuditTrail
	@Override
	public boolean contains(T comment) {
		if(comment==null || comment.getId()==null) return false;
		try {
			loadById(comment.getId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
}
