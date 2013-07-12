
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

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * TagRepository repository implementation.
 * 
 * @author Joosa Kurvinen
 */
@Repository
public class ProfileRepositoryMongoDBImpl extends AbstractCrudRepositoryMongoDBImpl<Profile> implements ProfileRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Profile loadByFacebookId(String facebookId) {
        Query query = new Query(Criteria.where("facebookId").is(facebookId));
        List<Profile> profiles = mongoTemplate.find(query, Profile.class);
        if(profiles.size()==0) return null;
        else return profiles.get(0);
    }

}
