package org.openinfinity.tagcloud.domain.specification;

import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Score;
import org.springframework.stereotype.Component;

@Component
public class ScoreSpecification extends AbstractSpecification<Score> {

	@Override
	public void testIfEligibleForCreation(Score entity,
			Collection<Score> entities) {
		// TODO Auto-generated method stub
		
	}}
