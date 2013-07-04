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
import org.openinfinity.tagcloud.domain.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:**/test-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentServiceImplTest {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
		commentRepository.dropCollection();
	}

	@Test 
	public void testCreateComment() {
		Comment expected = newTestComment();
		Comment comment = commentService.create(expected);
		Comment actual = commentService.loadById(comment.getId());
		assertEquals(expected.getText(), actual.getText());
		assertNotNull(actual.getId());
	}

	@Test 
	public void testCreateCommentWithSameText() {
		Comment comment = commentService.create(newTestComment());
		commentService.create(newTestComment());
		assertEquals(2, commentService.loadByText(comment.getText()).size());
	}

	

	@Test(expected=ApplicationException.class)
	public void testCreateCommentFailsWhenCommentAlreadyExists() {
		Comment comment = newTestComment();
		commentService.create(comment);
		commentService.create(comment);
	}
	
	@Test 
	public void testUpdateComment() {
		Comment comment = newTestComment();
		commentService.create(comment);
		comment = commentService.loadById(comment.getId());
		comment.setText("changed");
		commentService.update(comment);
		assertEquals("changed", commentService.loadById(comment.getId()).getText());
		assertEquals(1, commentService.loadAll().size());
	}
	
	@Test(expected=BusinessViolationException.class)
	public void testUpdateCommentFailsWhenCommentDoesNotExistYet() {
		Comment comment = newTestComment();
		commentService.update(comment);
	}
	
	
	@Test
	public void testDeleteComment() {
		Comment comment1 = newTestComment();
		commentService.create(comment1);
		assertAmountOfComments(1);
		Comment comment2 = newTestComment();
		comment2.setText("other text");
		commentService.create(comment2);
		assertAmountOfComments(2);
		commentService.delete(comment1);
		assertAmountOfComments(1);
		assertNotNull(commentRepository.loadById(comment2.getId()));
		assertNull(commentRepository.loadById(comment1.getId()));
	}

	
	@Test(expected=ApplicationException.class)
	public void testDeleteCommentFailsWhenCommentDoesNotExist() {
		Comment comment = newTestComment();
		commentService.delete(comment);
	}
	
	@Test
	public void testLoadById() {
		Comment expected = newTestComment();
		commentService.create(expected);
		Comment actual = commentService.loadById(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected=ApplicationException.class)
	public void testLoadByIdFailsWhenCommentDoesNotExist() {
		commentService.loadById("testcommentnotexisting");
	}
	
	

	private Comment newTestComment() {
		Profile profile = new Profile("testId"); 
		return new Comment("testi", profile);
	}

	private void assertAmountOfComments(int amount) {
		assertEquals(amount, commentRepository.loadAll().size());
	}
	

	
}
