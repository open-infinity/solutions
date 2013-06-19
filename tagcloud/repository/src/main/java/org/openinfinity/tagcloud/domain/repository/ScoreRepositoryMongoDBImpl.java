
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

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Collection;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.tagcloud.domain.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

/**
 * ScoreRepository repository implementation.
 * 
 * @author Ilkka Leinonen
 */
@Repository
public class ScoreRepositoryMongoDBImpl implements ScoreRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Log
	@AuditTrail
	public Score create(Score entity) {
		mongoTemplate.save(entity);
		return entity;
	}
	
	public void update(final org.openinfinity.tagcloud.domain.entity.Score entity) {
		Query query = new Query(Criteria.where("id").is(entity.getId()));
		final Update update = new Update();
		ReflectionUtils.doWithFields(entity.getClass(), new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				if (! field.isAccessible()) {
					field.setAccessible(Boolean.TRUE);
				}
				String key = field.getName();
				Object value = field.get(entity);
				update.addToSet(key, value);
			}
		});
		mongoTemplate.upsert(query, update, entity.getClass());
	}
	
	public Collection<Score> loadAll() {
		return mongoTemplate.findAll(Score.class);
	}
	
	public org.openinfinity.tagcloud.domain.entity.Score loadById(BigInteger id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findById(query, Score.class);
	}
	
	@Log
	@AuditTrail
	public Collection<Score> loadByText(String text) {
		Query query = new Query(Criteria.where("text").is(text));
		return mongoTemplate.find(query, Score.class);
	}
	
	public void delete (Score entity) {
		Query query = new Query(Criteria.where("id").is(entity.getId()));
		mongoTemplate.remove(query, entity.getClass());
	}
	
}
