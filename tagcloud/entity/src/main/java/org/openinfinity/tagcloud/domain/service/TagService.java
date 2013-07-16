package org.openinfinity.tagcloud.domain.service;

import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;

public interface TagService extends AbstractTextEntityCrudServiceInterface<Tag> {

	public List<Tag> searchLike(String input);

    void addTagToTarget(String tag, Target target, String facebookId);

	public static final String UNIQUE_EXCEPTION_ENTITY_ALREADY_EXISTS = "localized.exception.tag.already.exists";

}