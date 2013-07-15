package org.openinfinity.tagcloud.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:**/test-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TagServiceImplTest {
	
	@Autowired
	TagService tagService;
	
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
		Tag tag = tagService.create(expected);
		Tag actual = tagService.loadById(tag.getId());
		assertEquals(expected.getText(), actual.getText());
		assertNotNull(actual.getId());
	}

	@Test(expected=ApplicationException.class)
	public void testCreateTagFailsWhenTagAlreadyExists() {	
		Tag expected = new Tag("testi");
		Tag createdTag = tagService.create(expected);
		tagService.create(createdTag);
	}

	
	
	@Test 
	public void testUpdateTag() {
		Tag tag = new Tag("testi");
		tagService.create(tag);
		tag = tagService.loadById(tag.getId());
		tag.setText("changed");
		tagService.update(tag);
		assertEquals("changed", tagService.loadById(tag.getId()).getText());
		assertEquals(1, tagService.loadAll().size());
	}
	
	@Test(expected=BusinessViolationException.class)
	public void testUpdateTagFailsWhenTagDoesNotExistYet() {
		Tag tag = new Tag("testi");
		tagService.update(tag);
	}
	
	
	@Test
	public void testDeleteTag() {
		Tag tag1 = new Tag("testi");
		tagService.create(tag1);
		assertAmountOfTags(1);
		Tag tag2 = new Tag("testi2");
		tagService.create(tag2);
		assertAmountOfTags(2);
		tagService.delete(tag1);
		assertAmountOfTags(1);
		assertNotNull(tagRepository.loadById(tag2.getId()));
		assertNull(tagRepository.loadById(tag1.getId()));
	}

	
	@Test(expected=ApplicationException.class)
	public void testDeleteTagFailsWhenTagDoesNotExist() {
		Tag tag = new Tag("testi");
		tagService.delete(tag);
	}
	
	@Test
	public void testLoadById() {
		Tag expected = new Tag("testi");
		tagService.create(expected);
		Tag actual = tagService.loadById(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected=ApplicationException.class)
	public void testLoadByIdFailsWhenTagDoesNotExist() {
		tagService.loadById("testcommentnotexisting");
	}
	


	private void assertAmountOfTags(int amount) {
		assertEquals(amount, tagRepository.loadAll().size());
	}
	

	
}
