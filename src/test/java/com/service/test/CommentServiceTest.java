package com.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.dao.CommentDAO;
import com.domain.Comment;
import com.domain.Post;
import com.domain.User;
import com.service.impl.CommentServiceImpl;

public class CommentServiceTest {

	private CommentServiceImpl commentServiceImpl;
	private CommentDAO	commentDAO;

	@Before
	public void setUp() throws Exception {
		commentDAO = Mockito.mock(CommentDAO.class);
		commentServiceImpl = new CommentServiceImpl();
		commentServiceImpl.setCommentDAO(commentDAO);
	}

	@Test
	public void testGetAnExistingCommentById() throws Exception {
		int id = 1;
		Comment comment  = new Comment();
		comment.setId(id);
		
		when(commentDAO.getCommentById(id)).thenReturn(comment);
		Comment returned = commentServiceImpl.getCommentById(id);

		verify(commentDAO, times(1)).getCommentById(id);
		verifyNoMoreInteractions(commentDAO);

		assertEquals(comment.getId(), returned.getId());
	}

	@Test
	public void testGetNotExistingCommentById() throws Exception {
		int id = 10;
		
		when(commentDAO.getCommentById(id)).thenReturn(null);
		Comment returned = commentServiceImpl.getCommentById(id);

		verify(commentDAO, times(1)).getCommentById(id);
		verifyNoMoreInteractions(commentDAO);


		assertNull(returned);
	}

	@Test
	public void testGetAllExistingCommentsByPostId() throws Exception {
		int postId =1;
		ArrayList<Comment> list = new ArrayList<>();
		Post p = new Post(postId, "Test", new User(1, "Mike", "1000 N St", "mike@xyz.com"));
		list.add(new Comment(1, "comment1", p));
		list.add(new Comment(2, "comment2", p));
		
		when(commentDAO.getCommentsByPostId(postId)).thenReturn(list);

		List<Comment> returned = commentServiceImpl.getCommentsByPostId(postId);

		verify(commentDAO, times(1)).getCommentsByPostId(postId);

		verifyNoMoreInteractions(commentDAO);
		assertEquals(list.size(), returned.size());
		assertEquals(list.get(0).getId(), returned.get(0).getId());
		assertEquals(list.get(1).getId(), returned.get(1).getId());
		assertEquals(list.get(0).getPost().getId(), returned.get(0).getPost().getId());
		assertEquals(list.get(1).getPost().getId(), returned.get(1).getPost().getId());
	}

	@Test
	public void testNoPostsExist() throws Exception {
		int postId =1;
		ArrayList<Comment> list = new ArrayList<>();
		
		when(commentDAO.getCommentsByPostId(postId)).thenReturn(list);
		List<Comment> returned = commentServiceImpl.getCommentsByPostId(postId);

		verify(commentDAO, times(1)).getCommentsByPostId(postId);
		verifyNoMoreInteractions(commentDAO);

		assertEquals(list.size(), returned.size());
	}
	
	
}
