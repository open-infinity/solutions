package org.openinfinity.tagcloud.domain.service;

import org.openinfinity.tagcloud.domain.entity.Profile;

public interface ProfileService extends AbstractLocalizedExceptionCrudServiceInterface<Profile> {
	
	public Profile loadById(String id);
	
}