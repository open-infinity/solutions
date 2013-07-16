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
import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Score;
import org.openinfinity.tagcloud.domain.repository.ProfileRepository;
import org.openinfinity.tagcloud.domain.repository.ScoreRepository;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:**/test-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ScoreServiceImplTest {
	
	@Autowired
	ScoreService scoreService;
	
	@Autowired
	ScoreRepository scoreRepository;
	
	@Autowired
	TagRepository tagRepository;

	@Autowired
	TargetRepository targetRepository;

	@Autowired
	ProfileRepository profileRepository;

	
	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
		scoreRepository.dropCollection();
		targetRepository.dropCollection();
		tagRepository.dropCollection();
        profileRepository.dropCollection();
	}

    Profile testProfile = new Profile("testId");

	@Test 
	public void testCreateScore() {
		Score expected = new Score(3, testProfile);
		Score score = scoreService.create(expected);
		Score actual = scoreService.loadById(score.getId());
		assertEquals(expected.getStars(), actual.getStars());
		assertNotNull(actual.getId());
	}

	@Test(expected=ApplicationException.class)
	public void testCreateScoreFailsWhenScoreAlreadyExists() {	
		Score expected = new Score(3, testProfile);
		Score createdScore = scoreService.create(expected);
		scoreService.create(createdScore);
	}
	
	@Test 
	public void testUpdateScore() {
		Score score = new Score(3, testProfile);
		scoreService.create(score);
		score = scoreService.loadById(score.getId());
		score.setStars(5);
		scoreService.update(score);
		assertEquals(5, (int)scoreService.loadById(score.getId()).getStars());
		assertEquals(1, scoreService.loadAll().size());
	}
	
	@Test(expected=BusinessViolationException.class)
	public void testUpdateScoreFailsWhenScoreDoesNotExistYet() {
		Score score = new Score(3, testProfile);
		scoreService.update(score);
	}
	
	
	@Test
	public void testDeleteScore() {
		Score score1 = new Score(3,testProfile);
		scoreService.create(score1);
		assertAmountOfScores(1);
		Score score2 = new Score(3,testProfile);
		score2.setStars(4);
		scoreService.create(score2);
		assertAmountOfScores(2);
		scoreService.delete(score1);
		assertAmountOfScores(1);
		assertNotNull(scoreRepository.loadById(score2.getId()));
		assertNull(scoreRepository.loadById(score1.getId()));
	}

	
	@Test(expected=ApplicationException.class)
	public void testDeleteScoreFailsWhenScoreDoesNotExist() {
		Score score = new Score(3,testProfile);
		scoreService.delete(score);
	}
	
	@Test
	public void testLoadById() {
		Score expected = new Score(3,testProfile);
		scoreService.create(expected);
		Score actual = scoreService.loadById(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected=ApplicationException.class)
	public void testLoadByIdFailsWhenScoreDoesNotExist() {
		scoreService.loadById("testcommentnotexisting");
	}
	
	

	private void assertAmountOfScores(int amount) {
		assertEquals(amount, scoreRepository.loadAll().size());
	}
	

	
}
