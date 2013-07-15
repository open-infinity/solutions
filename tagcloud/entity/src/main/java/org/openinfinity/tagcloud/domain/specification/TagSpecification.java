package org.openinfinity.tagcloud.domain.specification;

import java.util.Collection;

import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.ExceptionLevel;
import org.openinfinity.core.util.ExceptionUtil;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.service.AbstractCrudServiceInterface;
import org.openinfinity.tagcloud.domain.service.TagService;
import org.springframework.stereotype.Component;

@Component
public class TagSpecification extends AbstractSpecification<Tag> {

	@Override
	public void testIfEligibleForCreation(Tag entity, Collection<Tag> entities) throws ApplicationException {
		//always ok
	}}
