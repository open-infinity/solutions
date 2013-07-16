package org.openinfinity.tagcloud.domain.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.*;

public interface CommentService extends AbstractTextEntityCrudServiceInterface<Comment> {

	Collection<Comment> loadByText(String text);
    
	void addCommentToTarget(String comment, Target target, String facebookId);

    	
}