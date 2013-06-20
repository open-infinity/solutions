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

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openinfinity.tagcloud.domain.service.TagSpecification;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TagRepositoryIntegrationTests {

	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	TagSpecification tagSpecification;
	
	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}
/*
	@Test
	public void givenKnownProductWhenStoringToBackendRepositoryThenProductMustBeFound() {
		Tag expected = createTag();
		Tag tag = tagRepository.create(expected);
		Tag actual = tagRepository.loadById(product.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getCompany(), actual.getCompany());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertNotNull(actual.getId());		
	}

	private Tag createTag() {
		Tag expected = new Tag();
		expected.s
		return expected;
	}
	*/
//	@Test @Ignore
//	public void testFail() {
//		fail("Not yet implemented");
//	}	

}
