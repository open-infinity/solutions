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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openinfinity.tagcloud.domain.entity.Location;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.service.TagSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tag Repository integration tests.
 * 
 * @Author Joosa Kurvinen 
 * 
 */
@ContextConfiguration(locations={"classpath*:**/test-repository-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationRepositoryIntegrationTests {

	@Autowired
	LocationRepository locationRepository;
	
	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
		locationRepository.dropCollection();
	}

	@Test
	public void testLoadByCoordinates() {
		Location loc1 = createTestLocation(30, 40);
		Location loc2 = createTestLocation(30.001, 40.001);
		double distance = 150; //meters
		locationRepository.create(loc1);
		Collection<Location> locations = locationRepository.loadByCoordinates(loc2.getLocation()[0], loc2.getLocation()[1], distance*1.1);
		assertEquals(1, locations.size());
		locations = locationRepository.loadByCoordinates(loc2.getLocation()[0], loc2.getLocation()[1], distance/1.1);
		assertEquals(0, locations.size());
		
	}
	
	private Location createTestLocation(double lon, double lat) {
		Location location = new Location();
		location.getLocation()[0] = lon;
		location.getLocation()[1] = lat;
		return location;
	}

//	@Test @Ignore
//	public void testFail() {
//		fail("Not yet implemented");
//	}	

}
