package org.openinfinity.tagcloud.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.tagcloud.domain.entity.Comment;
import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:**/test-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProfileServiceImplTest {
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	TargetService targetService;
	
	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
		profileRepository.dropCollection();
	}

	@Test 
	public void testCreateProfile() {
		Profile expected = createTestProfile();
		Profile profile = profileService.create(expected);
		Profile actual = profileService.loadById(profile.getId());
		assertNotNull(actual.getId());
	}

	@Test(expected=ApplicationException.class)
	public void testCreateProfileFailsWhenProfileAlreadyExists() {	
		Profile expected = createTestProfile();
		Profile createdProfile = profileService.create(expected);
		profileService.create(createdProfile);
	}
	
	@Test 
	public void testUpdateProfile() {
		Profile profile = createTestProfile();
		profileService.create(profile);
		profile = profileService.loadById(profile.getId());
		Target target = targetService.create(new Target("asdf",0,0));
		profile.getMyScoredTargets().add(target.getId());
		profileService.update(profile);
		assertEquals(1, profileService.loadById(profile.getId()).getMyScoredTargets().size());
		assertEquals(1, profileService.loadAll().size());
	}
	
	@Test(expected=BusinessViolationException.class)
	public void testUpdateProfileFailsWhenProfileDoesNotExistYet() {
		Profile profile = createTestProfile();
		profileService.update(profile);
	}
	
	
	@Test
	public void testDeleteProfile() {
		Profile profile1 = createTestProfile();
		profileService.create(profile1);
		assertAmountOfProfiles(1);
		Profile profile2 = createTestProfile();
		Target target = targetService.create(new Target("asdf",0,0));
		profile2.getMyScoredTargets().add(target.getId());
		profileService.create(profile2);
		assertAmountOfProfiles(2);
		profileService.delete(profile1);
		assertAmountOfProfiles(1);
		assertNotNull(profileRepository.loadById(profile2.getId()));
		assertNull(profileRepository.loadById(profile1.getId()));
	}

	
	@Test(expected=ApplicationException.class)
	public void testDeleteProfileFailsWhenProfileDoesNotExist() {
		Profile profile = createTestProfile();
		profileService.delete(profile);
	}
	
	@Test
	public void testLoadById() {
		Profile expected = createTestProfile();
		profileService.create(expected);
		Profile actual = profileService.loadById(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected=ApplicationException.class)
	public void testLoadByIdFailsWhenProfileDoesNotExist() {
		profileService.loadById("testiId");
	}
	
	

	private Profile createTestProfile() {
		Profile expected = new Profile("testId");
		return expected;
	}

	private void assertAmountOfProfiles(int amount) {
		assertEquals(amount, profileRepository.loadAll().size());
	}
	

	
}
