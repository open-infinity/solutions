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
		Tag expected = createTestTag();
		Tag tag = tagService.create(expected);
		Tag actual = tagService.loadById(tag.getId());
		assertEquals(expected.getText(), actual.getText());
		assertEquals(expected.getTargets().iterator().next().getText(), actual.getTargets().iterator().next().getText());
		assertNotNull(actual.getId());
	}

	@Test(expected=ApplicationException.class)
	public void testCreateTagFailsWhenTagAlreadyExists() {	
		Tag expected = createTestTag();
		Tag createdTag = tagService.create(expected);
		tagService.create(createdTag);
	}
	
	@Test 
	public void testUpdateTag() {
		Tag tag = createTestTag();
		tagService.create(tag);
		tag = tagService.loadById(tag.getId());
		tag.setText("changed");
		tagService.update(tag);
		assertEquals("changed", tagService.loadById(tag.getId()).getText());
		assertEquals(1, tagService.loadAll().size());
	}
	
	@Test(expected=BusinessViolationException.class)
	public void testUpdateTagFailsWhenTagDoesNotExistYet() {
		Tag tag = createTestTag();
		tagService.update(tag);
	}
	
	
	@Test
	public void testDeleteTag() {
		Tag tag1 = createTestTag();
		tagService.create(tag1);
		assertAmountOfTags(1);
		Tag tag2 = createTestTag();
		tag2.setText("other text");
		tagService.create(tag2);
		assertAmountOfTags(2);
		tagService.delete(tag1);
		assertAmountOfTags(1);
		assertNotNull(tagRepository.loadById(tag2.getId()));
		assertNull(tagRepository.loadById(tag1.getId()));
	}

	
	@Test(expected=ApplicationException.class)
	public void testDeleteTagFailsWhenTagDoesNotExist() {
		Tag tag = createTestTag();
		tagService.delete(tag);
	}
	
	@Test
	public void testLoadById() {
		Tag expected = createTestTag();
		tagService.create(expected);
		Tag actual = tagService.loadById(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected=ApplicationException.class)
	public void testLoadByIdFailsWhenTagDoesNotExist() {
		tagService.loadById(new BigInteger("3928102983740"));
	}
	
	

	private Tag createTestTag() {
		Tag expected = new Tag();
		expected.setText("testi");
		Target target = new Target();
		target.setText("testitarget");
		expected.getTargets().add(target);
		return expected;
	}

	private void assertAmountOfTags(int amount) {
		assertEquals(amount, tagRepository.loadAll().size());
	}
	

	
}
