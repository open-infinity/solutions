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
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.core.util.ExceptionUtil;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Product service implementation with specification.
 * 
 * @author Ilkka Leinonen
 */
@Service
public class TargetServiceImpl implements TargetService {

	@Autowired
	private TargetSpecification targetSpecification;
	
	@Autowired
	private TargetRepository targetRepository;
	
	@Log
	@AuditTrail
	public Target create(Target entity) {
		// FIX ME: Verify that entity does not allready exists.
		targetRepository.create(entity);
		return entity;
	}
	
	@Log
	@AuditTrail
	public void update(Target entity) {
		if (targetRepository.loadById(entity.getId()) == null) {
			ExceptionUtil.throwBusinessViolationException(
				"Entity does not exist: " + entity.getName(), 
				ExceptionLevel.ERROR, 
				TargetService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		targetRepository.update(entity);
	}
	
	public Collection<Target> loadAll() {
		return targetRepository.loadAll();
	}
	
	@Log
	@AuditTrail
	public Target loadById(BigInteger id) {
		Target entity = targetRepository.loadById(id);
		if (entity == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + id, 
				ExceptionLevel.WARNING, 
				TagService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		return entity; 
	}
	
	@Log
	@AuditTrail
	public void delete (Target entity) {
		if (targetRepository.loadById(entity.getId()) == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.INFORMATIVE, 
				TagService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		targetRepository.delete(entity);
	}

}
