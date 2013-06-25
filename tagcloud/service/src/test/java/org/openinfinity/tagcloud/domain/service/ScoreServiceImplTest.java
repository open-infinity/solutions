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
import org.openinfinity.tagcloud.domain.entity.Score;
import org.openinfinity.tagcloud.domain.repository.ScoreRepository;
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
	
	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
		scoreRepository.dropCollection();
	}

	@Test 
	public void testCreateScore() {
		Score expected = createTestScore();
		Score score = scoreService.create(expected);
		Score actual = scoreService.loadById(score.getId());
		assertEquals(expected.getStars(), actual.getStars());
		assertNotNull(actual.getId());
	}

	@Test(expected=ApplicationException.class)
	public void testCreateScoreFailsWhenScoreAlreadyExists() {	
		Score expected = createTestScore();
		Score createdScore = scoreService.create(expected);
		scoreService.create(createdScore);
	}
	
	@Test 
	public void testUpdateScore() {
		Score score = createTestScore();
		scoreService.create(score);
		score = scoreService.loadById(score.getId());
		score.setStars(5);
		scoreService.update(score);
		assertEquals(5, (int)scoreService.loadById(score.getId()).getStars());
		assertEquals(1, scoreService.loadAll().size());
	}
	
	@Test(expected=BusinessViolationException.class)
	public void testUpdateScoreFailsWhenScoreDoesNotExistYet() {
		Score score = createTestScore();
		scoreService.update(score);
	}
	
	
	@Test
	public void testDeleteScore() {
		Score score1 = createTestScore();
		scoreService.create(score1);
		assertAmountOfScores(1);
		Score score2 = createTestScore();
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
		Score score = createTestScore();
		scoreService.delete(score);
	}
	
	@Test
	public void testLoadById() {
		Score expected = createTestScore();
		scoreService.create(expected);
		Score actual = scoreService.loadById(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected=ApplicationException.class)
	public void testLoadByIdFailsWhenScoreDoesNotExist() {
		scoreService.loadById(new BigInteger("3928102983740"));
	}
	
	

	private Score createTestScore() {
		Score expected = new Score();
		expected.setStars(3);
		return expected;
	}

	private void assertAmountOfScores(int amount) {
		assertEquals(amount, scoreRepository.loadAll().size());
	}
	

	
}
