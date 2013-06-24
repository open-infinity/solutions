package org.openinfinity.tagcloud.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openinfinity.core.exception.ApplicationException;
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
		assertEquals(expected.getTargets().iterator().next().getName(), actual.getTargets().iterator().next().getName());
		assertNotNull(actual.getId());
	}

	@Test(expected=ApplicationException.class)
	public void testCreateTagFailsWhenTagAlreadyExists() {	
		Tag expected = createTestTag();
		Tag createdTag = tagService.create(expected);
		tagService.create(createdTag);
	}

	private Tag createTestTag() {
		Tag expected = new Tag();
		expected.setText("testi");
		Target target = new Target();
		target.setName("testitarget");
		expected.getTargets().add(target);
		return expected;
	}

	
}
