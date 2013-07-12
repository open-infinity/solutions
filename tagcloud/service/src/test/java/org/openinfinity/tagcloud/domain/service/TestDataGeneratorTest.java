package org.openinfinity.tagcloud.domain.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openinfinity.tagcloud.domain.repository.ProfileRepository;
import org.openinfinity.tagcloud.domain.repository.ScoreRepository;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.domain.service.testdata.TestDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:**/test-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDataGeneratorTest {

	@Autowired
	TestDataGenerator testDataGenerator;
	
	@Autowired
	TargetService targetService;
	
	@Autowired
	TagService tagService;
	
	@Autowired
	TargetRepository targetRepository;
	
	@Autowired
	TagRepository tagRepository;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    @Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		tagRepository.dropCollection();
		targetRepository.dropCollection();
        profileRepository.dropCollection();
        scoreRepository.dropCollection();
	}

	@Test @Ignore
	public void test() {
		testDataGenerator.generate();
		//assertEquals(1, targetRepository.loadAll().size());
	}

}
