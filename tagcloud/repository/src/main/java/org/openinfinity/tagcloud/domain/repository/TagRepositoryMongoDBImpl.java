
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
package org.openinfinity.tagcloud.domain.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * TagRepository repository implementation.
 * 
 * @author Joosa Kurvinen
 */
@Repository
public class TagRepositoryMongoDBImpl extends AbstractCrudRepositoryMongoDBImpl<Tag> implements TagRepository {
	
	@Autowired
	MongoTemplate mongoTemplate;

	private static final Logger LOGGER = Logger.getLogger(TagRepositoryMongoDBImpl.class);

	
	@Override
	public List<Tag> searchLike(String input) {
		Query query = new Query(Criteria.where("text").regex("\\b"+input+"\\.*", "i"));
		List<Tag> tags = mongoTemplate.find(query, Tag.class);
		return tags;
	}
	

}
