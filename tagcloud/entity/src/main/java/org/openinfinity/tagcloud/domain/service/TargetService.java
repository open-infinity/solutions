package org.openinfinity.tagcloud.domain.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.entity.query.Result;

public interface TargetService extends AbstractCrudServiceInterface<Target, BigInteger> {
	
	public void addTagToTarget(Tag tag, Target target);
	
	void removeTagFromTarget(Tag tag, Target target);

	public static final String UNIQUE_EXCEPTION_TAG_ALREADY_INCLUDED = "localized.exception.tag.already.included";

	public static final String UNIQUE_EXCEPTION_TAG_NOT_INCLUDED = "localized.exception.tag.not.included";

	Collection<Target> loadByTag(Tag tag);

	List<Result> loadByQuery(List<Tag> required, List<Tag> preferred,
			List<Tag> nearby, double longitude, double latitude, double radius);
	
}