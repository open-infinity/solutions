package org.openinfinity.tagcloud.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.entity.query.NearbyTarget;
import org.openinfinity.tagcloud.domain.entity.query.Recommendation;
import org.openinfinity.tagcloud.domain.entity.query.TagQuery;
import org.openinfinity.tagcloud.domain.repository.ProfileRepository;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:**/test-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TargetServiceImplTest {
	
	@Autowired
	TargetService targetService;
	
	@Autowired
	TagService tagService;
	
	@Autowired
	TargetRepository targetRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
	ProfileService profileService;
	
	Profile profile;

    
	@Before
	public void setUp() throws Exception {
		profile = profileService.create(new Profile("testId"));
	}

	@After
	public void tearDown() throws Exception {
		targetRepository.dropCollection();
		tagRepository.dropCollection();
        profileRepository.dropCollection();
	}

	
	@Test 
	public void testCreateTarget() {
		Target expected = createTestTarget();
		Target actual = targetService.loadById(expected.getId());
		assertEquals(expected.getText(), actual.getText());
		assertNotNull(actual.getId());
	}

	@Test 
	public void testCreateTargetWithSameText() {
		Target expected = createTestTarget("abc", new ArrayList<Tag>(), 0, 0);
		createTestTarget("abc", new ArrayList<Tag>(), 20, 20);
		assertEquals(2, targetService.loadByText(expected.getText()).size());
	}

	

	@Test 
	public void testUpdateTarget() {
		Target target = createTestTarget();
		target = targetService.loadById(target.getId());
		target.setText("changed");
		targetService.update(target);
		assertEquals("changed", targetService.loadById(target.getId()).getText());
		assertEquals(1, targetService.loadAll().size());
	}
	
	@Test(expected=BusinessViolationException.class) 
	public void testUpdateTargetFailsWhenTargetDoesNotExistYet() {
		Target target = new Target("asdf",0,0);
		target.setText("test");
		targetService.update(target);
	}
	
	
	@Test 
	public void testDeleteTarget() {
		Target target1 = createTestTarget();
		assertAmountOfTargets(1);
		Target target2 = createTestTarget();
		assertAmountOfTargets(2);
		targetService.delete(target1);
		assertAmountOfTargets(1);
		assertNotNull(targetRepository.loadById(target2.getId()));
		assertNull(targetRepository.loadById(target1.getId()));
	}

	
	@Test(expected=ApplicationException.class) 
	public void testDeleteTargetFailsWhenTargetDoesNotExist() {
		Target target = new Target("asdf",0,0);
		target.setText("test");
		targetService.delete(target);
	}
	
	@Test 
	public void testLoadById() {
		Target expected = createTestTarget();
		Target actual = targetService.loadById(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected=ApplicationException.class) 
	public void testLoadByIdFailsWhenTargetDoesNotExist() {
		targetService.loadById("testcommentnotexisting");
	}
	


	@Test 
	public void testLoadByTag() {
		Target target = createTestTarget();
		
		Tag tag = new Tag("testi");
		Tag differentTag = new Tag("testi2");
		
		tagService.addTagToTarget(tag.getText(), target, profile.getFacebookId());
		
		assertEquals(1, targetService.loadByTag(tag).size());
		assertEquals(0, targetService.loadByTag(differentTag).size());
	}
		
	
	@Test 
	public void testLoadByQueryBasic() {
		Tag tag1 = new Tag("t1");
		Tag tag2 = new Tag("t2");
		Tag tag3 = new Tag("t3");
		createTestTarget(Utils.createList(tag1, tag2));
		
		List<Tag> testListOk = new ArrayList<Tag>();
		testListOk.add(tag1);
		testListOk.add(tag2);
		TagQuery query = (new TagQuery(0, 0, 200)).requireTags(testListOk);
		System.out.println("query1");
		assertEquals(1, targetService.loadByQuery(query).size());
		
		List<Tag> testListFail = new ArrayList<Tag>();
		testListFail.add(tag1);
		testListFail.add(tag3);
		query = (new TagQuery(0, 0, 200)).requireTags(testListFail);
		System.out.println("query2");
		assertEquals(0, targetService.loadByQuery(query).size());
	}
	
	
	@Test 
	public void testLoadByNearQuery1() {
		Tag shop = new Tag("shop");
		Tag gym = new Tag("gym");
		double[] aLoc = Utils.calcLocation(30, 40, -1.1*NearbyTarget.MAX_DISTANCE, 0);
		Target a = createTestTarget("a", Utils.createList(shop), aLoc[0], aLoc[1]);
		double[] bLoc = Utils.calcLocation(30, 40, -0.9*NearbyTarget.MAX_DISTANCE, 0);
		Target b = createTestTarget("b", Utils.createList(shop), bLoc[0], bLoc[1]);
		Target c = createTestTarget("c", Utils.createList(gym), 30, 40);
		double[] dLoc = Utils.calcLocation(30, 40, 2*NearbyTarget.MAX_DISTANCE, 0);
		Target d = createTestTarget("d", Utils.createList(shop, gym), dLoc[0], dLoc[1]);
		
		TagQuery query = (new TagQuery(30, 40, 5000)).requireTags(Utils.createList(shop)).nearbyTags(Utils.createList(gym));
		List<Recommendation> results = targetService.loadByQuery(query);
		assertEquals(false, resultsContainsTarget(results, a));
		assertEquals(true, resultsContainsTarget(results, b));
		assertEquals(false, resultsContainsTarget(results, c));
		assertEquals(true, resultsContainsTarget(results, d));
	}
	
	private boolean resultsContainsTarget(List<Recommendation> results, Target target) {
		for(Recommendation result : results) {
			if (result.getTarget().equals(target)) return true;
		}
		return false;
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

	
	
	
	private void assertAmountOfTargets(int amount) {
		assertEquals(amount, targetRepository.loadAll().size());
	}
	
	
	
}
