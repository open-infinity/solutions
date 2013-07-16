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
import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.ProfileRepository;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:**/test-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TagServiceImplTest {

	@Autowired
	TagRepository tagRepository;

	@Autowired
	TargetRepository targetRepository;

	@Autowired
	ProfileRepository profileRepository;

	
	
	@Autowired
	TagService tagService;
	

	@Autowired
	ProfileService profileService;
	
	@Autowired
	TargetService targetService;
	
	
	Profile profile;

    
	@Before
	public void setUp() throws Exception {
		profile = profileService.create(new Profile("testId"));
	}
	
	@After
	public void tearDown() throws Exception {
		tagRepository.dropCollection();
        profileRepository.dropCollection();
        targetRepository.dropCollection();
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
	

	@Test 
	public void testAddTagToTarget() {
		Target target = createTestTarget();
		
		Tag tag = new Tag("test tag");
		tagService.addTagToTarget(tag.getText(), target, profile.getFacebookId());
		target = targetService.loadById(target.getId());
		tag = tagService.loadByText(tag.getText()).iterator().next();
		
		assertEquals(1, tagService.loadAll().size());
		assertEquals("test tag", targetService.loadById(target.getId()).getTags().iterator().next().getText());
		assertEquals(true, profileService.loadById(profile.getId()).getMyTags().get(target.getId()).contains(tag));
	}
	
	@Test(expected=BusinessViolationException.class)  
	public void testAddTagToTargetFailsIfTagAlreadyExists() {
		Target target = createTestTarget();
		
		tagService.addTagToTarget("test", target, profile.getFacebookId());
		tagService.addTagToTarget("test", target, profile.getFacebookId());
	}

	private final String UNIQUE_RANDOM_NAME = "unique.random.name";
	private Target createTestTarget(String text, List<Tag> tags, double longitude, double latitude) {
		Target target = new Target(text,longitude,latitude);
		if(text.equals(UNIQUE_RANDOM_NAME)) {
			target.setText("name"+Math.random()+System.currentTimeMillis());
		}
		else target.setText(text);
		target.setLocation(longitude, latitude);
		targetService.create(target);
		
		for(Tag tag : tags) {
			tagService.addTagToTarget(tag.getText(), target, profile.getFacebookId());
		}
		
		return target;
	}

	private Target createTestTarget() {
		return createTestTarget(UNIQUE_RANDOM_NAME, new ArrayList<Tag>(), 0, 0);
	}
	
	private Target createTestTarget(double longitude, double latitude) {
		return createTestTarget(UNIQUE_RANDOM_NAME, new ArrayList<Tag>(), longitude, latitude);
	}
	
	private Target createTestTarget(List<Tag> tags) {
		return createTestTarget(UNIQUE_RANDOM_NAME, tags, 0, 0);
	}

	
	
	
}
