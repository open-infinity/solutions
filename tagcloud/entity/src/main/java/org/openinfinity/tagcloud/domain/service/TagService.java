package org.openinfinity.tagcloud.domain.service;

import java.math.BigInteger;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Tag;

public interface TagService extends AbstractCrudServiceInterface<Tag> {

	public List<Tag> searchLike(String input);
	
}