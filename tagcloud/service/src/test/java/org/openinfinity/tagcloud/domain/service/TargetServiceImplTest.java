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
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:**/test-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TargetServiceImplTest {
	
	@Autowired
	TargetService targetService;
	
	@Autowired
	TargetRepository targetRepository;
	
	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
		targetRepository.dropCollection();
	}

	@Test 
	public void testCreateTarget() {
		Target expected = createTestTarget();
		Target target = targetService.create(expected);
		Target actual = targetService.loadById(target.getId());
		assertEquals(expected.getText(), actual.getText());
		assertNotNull(actual.getId());
	}

	@Test(expected=ApplicationException.class)
	public void testCreateTargetFailsWhenTargetAlreadyExists() {	
		Target expected = createTestTarget();
		Target createdTarget = targetService.create(expected);
		targetService.create(createdTarget);
	}
	
	@Test 
	public void testUpdateTarget() {
		Target target = createTestTarget();
		targetService.create(target);
		target = targetService.loadById(target.getId());
		target.setText("changed");
		targetService.update(target);
		assertEquals("changed", targetService.loadById(target.getId()).getText());
		assertEquals(1, targetService.loadAll().size());
	}
	
	@Test(expected=BusinessViolationException.class)
	public void testUpdateTargetFailsWhenTargetDoesNotExistYet() {
		Target target = createTestTarget();
		targetService.update(target);
	}
	
	
	@Test
	public void testDeleteTarget() {
		Target target1 = createTestTarget();
		targetService.create(target1);
		assertAmountOfTargets(1);
		Target target2 = createTestTarget();
		target2.setText("other text");
		targetService.create(target2);
		assertAmountOfTargets(2);
		targetService.delete(target1);
		assertAmountOfTargets(1);
		assertNotNull(targetRepository.loadById(target2.getId()));
		assertNull(targetRepository.loadById(target1.getId()));
	}

	
	@Test(expected=ApplicationException.class)
	public void testDeleteTargetFailsWhenTargetDoesNotExist() {
		Target target = createTestTarget();
		targetService.delete(target);
	}
	
	@Test
	public void testLoadById() {
		Target expected = createTestTarget();
		targetService.create(expected);
		Target actual = targetService.loadById(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected=ApplicationException.class)
	public void testLoadByIdFailsWhenTargetDoesNotExist() {
		targetService.loadById(new BigInteger("3928102983740"));
	}
	
	

	private Target createTestTarget() {
		Target expected = new Target();
		expected.setText("testi");
		return expected;
	}

	private void assertAmountOfTargets(int amount) {
		assertEquals(amount, targetRepository.loadAll().size());
	}
	

	
}
