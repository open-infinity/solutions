
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

import java.math.BigInteger;
import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Location;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * LocationRepository repository implementation.
 * 
 * @author Joosa Kurvinen
 */
@Repository
public class LocationRepositoryMongoDBImpl extends AbstractCrudRepositoryMongoDBImpl<Location, BigInteger> implements LocationRepository {
	
	
	
	@Override
	public Location create(Location entity) {
		Location location = super.create(entity);
		return location;
	}

	@Override
	public Collection<Location> loadByCoordinates(double longitude, double latitude, double radius) {
		Query query = new Query(Criteria.where("location").nearSphere(new Point(longitude, latitude)).maxDistance(radius/6371000));
		return mongoTemplate.find(query, Location.class);
	}

}
