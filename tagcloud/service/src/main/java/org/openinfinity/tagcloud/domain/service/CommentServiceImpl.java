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
import org.openinfinity.tagcloud.domain.entity.Comment;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Product service implementation with specification.
 * 
 * @author Ilkka Leinonen
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentSpecification commentSpecification;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Log
	@AuditTrail
	@Override
	public Comment create(Comment entity) {
		Collection<Comment> entities = commentRepository.loadByText(entity.getText());
		if (commentSpecification.isNotEligibleForCreation(entity, entities)) {
			ExceptionUtil.throwApplicationException(
				"Entity already exists: " + entity.getText(), 
				ExceptionLevel.INFORMATIVE, 
				CommentService.UNIQUE_EXCEPTION_ENTITY_ALREADY_EXISTS);
		}
		commentRepository.create(entity);
		return entity;
	}
	
	@Log
	@AuditTrail
	public void update(Comment entity) {
		if (commentRepository.loadById(entity.getId()) == null) {
			ExceptionUtil.throwBusinessViolationException(
				"Entity does not exist: " + entity.getText(), 
				ExceptionLevel.ERROR, 
				CommentService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		commentRepository.update(entity);
	}
	
	public Collection<Comment> loadAll() {
		return commentRepository.loadAll();
	}
	
	@Log
	@AuditTrail
	public Comment loadById(BigInteger id) {
		Comment entity = commentRepository.loadById(id);
		if (entity == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + id, 
				ExceptionLevel.WARNING, 
				CommentService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		return entity; 
	}
	
	@Log
	@AuditTrail
	public void delete (Comment entity) {
		if (commentRepository.loadById(entity.getId()) == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.INFORMATIVE, 
				CommentService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		commentRepository.delete(entity);
	}

	@Override
	public boolean contains(Comment comment) {
		if(comment==null || comment.getId()==null) return false;
		try {
			loadById(comment.getId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
