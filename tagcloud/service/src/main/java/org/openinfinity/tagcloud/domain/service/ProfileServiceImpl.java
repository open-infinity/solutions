/*
 * Copyright (c) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openinfinity.tagcloud.domain.service;

import java.math.BigInteger;
import java.util.Collection;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.exception.ExceptionLevel;
import org.openinfinity.core.util.ExceptionUtil;
import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Product service implementation with specification.
 * 
 * @author Ilkka Leinonen
 */
@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileSpecification profileSpecification;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Log
	@AuditTrail
	public Profile create(Profile entity) {
		//FIXME: Verify that the profile does not exist, if exists the update groups.
		profileRepository.create(entity);
		return entity;
	}
	
	@Log
	@AuditTrail
	public void update(Profile entity) {
		if (profileRepository.loadById(entity.getId()) != null) {
			ExceptionUtil.throwBusinessViolationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.ERROR, 
				ProfileService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		profileRepository.update(entity);
	}
	
	public Collection<Profile> loadAll() {
		return profileRepository.loadAll();
	}
	
	@Log
	@AuditTrail
	public Profile loadById(String id) {
		Profile entity = profileRepository.loadById(id);
		if (entity == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + id, 
				ExceptionLevel.WARNING, 
				ProfileService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		return entity; 
	}
	
	@Log
	@AuditTrail
	public void delete (Profile entity) {
		if (profileRepository.loadById(entity.getId()) != null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.INFORMATIVE, 
				ProfileService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		profileRepository.delete(entity);
	}

	@Override
	public Profile loadById(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
