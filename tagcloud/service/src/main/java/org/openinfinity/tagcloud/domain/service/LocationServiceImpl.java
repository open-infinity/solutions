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
import org.openinfinity.tagcloud.domain.entity.Location;
import org.openinfinity.tagcloud.domain.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Product service implementation with specification.
 * 
 * @author Ilkka Leinonen
 */
@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationSpecification locationSpecification;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Log
	@AuditTrail
	public Location create(Location entity) {
		// FIX ME: Verify that entity does not allready exists.
		locationRepository.create(entity);
		return entity;
	}
	
	@Log
	@AuditTrail
	public void update(Location entity) {
		if (locationRepository.loadById(entity.getId()) != null) {
			ExceptionUtil.throwBusinessViolationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.ERROR, 
				LocationService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		locationRepository.update(entity);
	}
	
	public Collection<Location> loadAll() {
		return locationRepository.loadAll();
	}
	
	@Log
	@AuditTrail
	public Location loadById(BigInteger id) {
		Location entity = locationRepository.loadById(id);
		if (entity == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + id, 
				ExceptionLevel.WARNING, 
				LocationService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		return entity; 
	}
	
	@Log
	@AuditTrail
	public void delete (Location entity) {
		if (locationRepository.loadById(entity.getId()) != null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.INFORMATIVE, 
				LocationService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		locationRepository.delete(entity);
	}
	
}
