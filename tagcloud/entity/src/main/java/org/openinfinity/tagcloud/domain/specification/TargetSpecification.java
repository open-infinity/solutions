package org.openinfinity.tagcloud.domain.specification;

import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Target;
import org.springframework.stereotype.Component;

@Component
public class TargetSpecification extends AbstractSpecification<Target> {

	@Override
	public void testIfEligibleForCreation(Target entity,
			Collection<Target> entities) {
		// TODO Auto-generated method stub
		
	}

	
}

