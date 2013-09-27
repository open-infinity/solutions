package org.openinfinity.tagcloud.domain.service;

import java.util.Collection;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.entity.query.Recommendation;
import org.openinfinity.tagcloud.domain.entity.query.TagQuery;

public interface TargetService extends AbstractTextEntityCrudServiceInterface<Target> {
	
	public static final String UNIQUE_EXCEPTION_TAG_ALREADY_INCLUDED = "localized.exception.tag.already.included";

	public static final String UNIQUE_EXCEPTION_TAG_NOT_INCLUDED = "localized.exception.tag.not.included";

	Collection<Target> loadByTag(Tag tag);

	List<Recommendation> loadByQuery(TagQuery tagQuery);

	List<Target> searchLike(String input);

	List<Target> searchFromTags(String tag);
	
	List<Target> searchAll();
	
	void setFacebookTargets(List<Target> targets, boolean clear);

    //void removeTagFromTarget(Tag tag, Target target);
}