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
	
	void addTagToTarget(Tag tag, Target target, Profile profile);
	
	void removeTagFromTarget(Tag tag, Target target);

	public static final String UNIQUE_EXCEPTION_TAG_ALREADY_INCLUDED = "localized.exception.tag.already.included";

	public static final String UNIQUE_EXCEPTION_TAG_NOT_INCLUDED = "localized.exception.tag.not.included";

	Collection<Target> loadByTag(Tag tag);

	List<Result> loadByQuery(TagQuery tagQuery);

	void addScoreToTarget(Score score, Target target);


    @Log
    @AuditTrail
    @Transactional
    void addCommentToTarget(Comment comment, Target target);
}