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
import java.util.List;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.exception.ExceptionLevel;
import org.openinfinity.core.util.ExceptionUtil;
import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Product service implementation with specification.
 * 
 * @author Ilkka Leinonen
 */
@Service
public class TagServiceImpl extends AbstractTextEntityCrudServiceImpl<Tag> implements TagService {

	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private ProfileService profileService;

	@Autowired
	private TargetService targetService;

	@Override
	public List<Tag> searchLike(String input) {
		return tagRepository.searchLike(input);
	}
	

    @Log
	@AuditTrail
	@Override
	@Transactional
	public void addTagToTarget(String tagName, Target target, String facebookId) {
    	Tag tag = new Tag(tagName);
        
    	if(target.getTags().contains(tag)) {
			ExceptionUtil.throwBusinessViolationException(
					"Tag with the same name already exists in the target",
					ExceptionLevel.INFORMATIVE,
					TargetService.UNIQUE_EXCEPTION_TAG_ALREADY_INCLUDED);
		}
        
    	Profile profile = profileService.loadByFacebookId(facebookId);
        
    	tag = create(tag);

		target.getTags().add(tag);
		targetService.update(target);

		profile.addTag(tag, target);
		profileService.update(profile);
	}

}