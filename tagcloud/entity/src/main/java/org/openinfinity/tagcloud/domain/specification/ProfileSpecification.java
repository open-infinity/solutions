package org.openinfinity.tagcloud.domain.specification;

import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileSpecification extends AbstractSpecification<Profile> {

	@Override
	public void testIfEligibleForCreation(Profile entity,
			Collection<Profile> entities) {
		
	}}
