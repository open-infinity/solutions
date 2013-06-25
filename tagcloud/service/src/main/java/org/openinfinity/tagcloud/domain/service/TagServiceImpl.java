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
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Product service implementation with specification.
 * 
 * @author Ilkka Leinonen
 */
@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagSpecification tagSpecification;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Log
	@AuditTrail
	public Tag create(Tag entity) {
		Collection<Tag> entities = tagRepository.loadByText(entity.getText());
		if (tagSpecification.isNotEligibleForCreation(entity, entities)) {
			System.out.println("Throwing exception");
			ExceptionUtil.throwApplicationException(
				"Entity already exists: " + entity.getText(), 
				ExceptionLevel.INFORMATIVE, 
				TagService.UNIQUE_EXCEPTION_ENTITY_ALREADY_EXISTS);
		}
		tagRepository.create(entity);
		return entity;
	}
	
	@Log
	@AuditTrail
	public void update(Tag entity) {
		if (tagRepository.loadById(entity.getId()) != null) {
			ExceptionUtil.throwBusinessViolationException(
				"Entity does not exist: " + entity.getText(), 
				ExceptionLevel.ERROR, 
				TagService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		tagRepository.update(entity);
	}
	
	public Collection<Tag> loadAll() {
		return tagRepository.loadAll();
	}
	
	@Log
	@AuditTrail
	public Tag loadById(BigInteger id) {
		Tag entity = tagRepository.loadById(id);
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
	public void delete (Tag entity) {
		if (tagRepository.loadById(entity.getId()) != null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.INFORMATIVE, 
				TagService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		tagRepository.delete(entity);
	}
	
}