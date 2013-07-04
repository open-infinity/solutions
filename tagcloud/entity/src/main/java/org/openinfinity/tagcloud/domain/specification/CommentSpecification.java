package org.openinfinity.tagcloud.domain.specification;

import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentSpecification extends AbstractSpecification<Comment> {

	@Override
	public void testIfEligibleForCreation(Comment entity,
			Collection<Comment> entities) {
		
	}


	
	
}
