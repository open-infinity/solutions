package org.openinfinity.tagcloud.domain.service;

import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Tag;

public abstract class AbstractSpecification<T> {
	
	public boolean isNotEligibleForCreation(T entity, Collection<T> entities) {
		return entities.contains(entity);
	}

}
