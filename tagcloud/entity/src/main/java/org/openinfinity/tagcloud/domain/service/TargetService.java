package org.openinfinity.tagcloud.domain.service;

import java.util.Collection;
import java.util.List;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.tagcloud.domain.entity.*;
import org.openinfinity.tagcloud.domain.entity.query.Result;
import org.openinfinity.tagcloud.domain.entity.query.TagQuery;
import org.springframework.transaction.annotation.Transactional;

public interface TargetService extends AbstractTextEntityCrudServiceInterface<Target> {
	

	public static final String UNIQUE_EXCEPTION_TAG_ALREADY_INCLUDED = "localized.exception.tag.already.included";

	public static final String UNIQUE_EXCEPTION_TAG_NOT_INCLUDED = "localized.exception.tag.not.included";

	Collection<Target> loadByTag(Tag tag);

	List<Result> loadByQuery(TagQuery tagQuery);

    void addTagToTarget(String tag, Target target, String facebookId);

    void removeTagFromTarget(Tag tag, Target target);

    void addCommentToTarget(String comment, Target target, String facebookId);

    void scoreTarget(int scoreStars, Target target, String facebookId);
}