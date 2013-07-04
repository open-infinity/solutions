package org.openinfinity.tagcloud.domain.service;

import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Tag;

public interface TagService extends AbstractTextEntityCrudServiceInterface<Tag> {

	public List<Tag> searchLike(String input);
	
	public static final String UNIQUE_EXCEPTION_ENTITY_ALREADY_EXISTS = "localized.exception.tag.already.exists";

}