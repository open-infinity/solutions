
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * TargetRepository repository implementation.
 * 
 * @author Joosa Kurvinen
 */
@Repository
public class TargetRepositoryMongoDBImpl extends AbstractCrudRepositoryMongoDBImpl<Target> implements TargetRepository {
	@Override
	public List<Target> loadByCoordinates(double longitude, double latitude, double radius) {
		Point location = new Point(longitude, latitude);
		NearQuery query = NearQuery.near(location).maxDistance(new Distance(radius/1000, Metrics.KILOMETERS));
		GeoResults<Target> targets = mongoTemplate.geoNear(query, Target.class);
		return getContentFromGeoResults(targets);
	}

	private List<Target> getContentFromGeoResults(GeoResults<Target> geoResults) {
		List<Target> list = new ArrayList<Target>();
		for (GeoResult<Target> result : geoResults.getContent()) {
			list.add(result.getContent());
		}
		return list;
	}
	
	
	@Override 
	public Collection<Target> loadByTag(Tag tag){
		Query query = new Query(Criteria.where("tags").elemMatch(Criteria.where("text").is(tag.getText())));
		return mongoTemplate.find(query, Target.class);
	}

	@Override
	public void dropCollection() {
		mongoTemplate.indexOps(Target.class).dropAllIndexes();
		mongoTemplate.dropCollection(Target.class);
		mongoTemplate.indexOps(Target.class).ensureIndex(new GeospatialIndex("location"));
		
		
	}
	
	
	
}
