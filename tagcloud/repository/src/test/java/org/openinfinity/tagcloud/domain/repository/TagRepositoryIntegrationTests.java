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
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.specification.TagSpecification;
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
public class TagRepositoryIntegrationTests {

	@Autowired
	TagRepository tagRepository;
	
	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
		tagRepository.dropCollection();
	}

	@Test
	public void testCreateTag() {
		Tag expected = new Tag("testi");
		Tag tag = tagRepository.create(expected);
		Tag actual = tagRepository.loadById(tag.getId());		
		assertEquals(expected.getText(), actual.getText());
		assertNotNull(actual.getId());
	}
	
	@Test
	public void testUpdateTag() {
		Tag tag = new Tag("testi");
		tagRepository.create(tag);
		String updatedText = "testi2";
		tag.setText(updatedText);
		tagRepository.update(tag);
		assertAmountOfTags(1);
		assertEquals(updatedText, tagRepository.loadById(tag.getId()).getText());
	}
	
	@Test
	public void testDeleteTag() {
		Tag tag1 = new Tag("testi");
		tagRepository.create(tag1);
		assertAmountOfTags(1);
		Tag tag2 = new Tag("testi2");
		tagRepository.create(tag2);
		assertAmountOfTags(2);
		tagRepository.delete(tag1);
		assertAmountOfTags(1);
		assertNotNull(tagRepository.loadById(tag2.getId()));
		assertNull(tagRepository.loadById(tag1.getId()));
	}

	@Test
	public void testFindByText() {
		Tag expected = new Tag("testi");
		Tag tag = tagRepository.create(expected);
		Collection<Tag> actual = tagRepository.loadByText(tag.getText());
		assertEquals(1, actual.size());
		assertEquals(expected.getText(), actual.iterator().next().getText());
	}
	
	
	private void assertAmountOfTags(int amount) {
		assertEquals(amount, tagRepository.loadAll().size());
	}
	


}
