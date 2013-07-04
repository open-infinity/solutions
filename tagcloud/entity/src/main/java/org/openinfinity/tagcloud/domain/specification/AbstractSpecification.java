package org.openinfinity.tagcloud.domain.specification;

import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Tag;

public abstract class AbstractSpecification<T> {
	
	public abstract void testIfEligibleForCreation(T entity, Collection<T> entities);

	
	
}
