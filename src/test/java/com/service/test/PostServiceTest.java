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

import com.dao.PostDAO;
import com.domain.Post;
import com.domain.User;
import com.service.impl.PostServiceImpl;

public class PostServiceTest {

	private PostServiceImpl postServiceImpl;
	private PostDAO postDAO;

	@Before
	public void setUp() throws Exception {
		postDAO = Mockito.mock(PostDAO.class);
		postServiceImpl = new PostServiceImpl();
		postServiceImpl.setPostDAO(postDAO);
	}

	@Test
	public void testGetAnExistingPostById() throws Exception {
		int id = 1;
		Post p = new Post();
		p.setId(id);

		when(postDAO.getPostById(id)).thenReturn(p);
		Post returned = postServiceImpl.getPostById(id);

		verify(postDAO, times(1)).getPostById(id);
		verifyNoMoreInteractions(postDAO);

		assertEquals(p.getId(), returned.getId());
	}

	@Test
	public void testGetNotExistingPostById() throws Exception {
		int id = 10;

		when(postDAO.getPostById(id)).thenReturn(null);
		Post returned = postServiceImpl.getPostById(id);

		verify(postDAO, times(1)).getPostById(id);
		verifyNoMoreInteractions(postDAO);

		assertNull(returned);
	}

	@Test
	public void getAllExistingPostsByUser() throws Exception {
		int userId = 1;
		List<Post> posts = getPostsData(userId);

		when(postDAO.getPostsByUserId(userId)).thenReturn(posts);

		List<Post> returned = postServiceImpl.getPostsByUserId(userId);

		verify(postDAO, times(1)).getPostsByUserId(userId);

		verifyNoMoreInteractions(postDAO);
		assertEquals(posts.size(), returned.size());
		assertEquals(posts.get(0).getId(), returned.get(0).getId());
		assertEquals(posts.get(1).getId(), returned.get(1).getId());
		assertEquals(posts.get(0).getUser().getId(), returned.get(0).getUser().getId());
		assertEquals(posts.get(1).getUser().getId(), returned.get(1).getUser().getId());
	}

	@Test
	public void testNoPostsExist() throws Exception {
		int userId = 1;
		ArrayList<Post> list = new ArrayList<>();
		when(postDAO.getPostsByUserId(userId)).thenReturn(list);

		List<Post> returned = postServiceImpl.getPostsByUserId(userId);

		verify(postDAO, times(1)).getPostsByUserId(userId);

		verifyNoMoreInteractions(postDAO);

		assertEquals(list.size(), returned.size());
	}

	@Test
	public void getAllExistingPosts() throws Exception {
		int userId = 1;
		List<Post> posts = getPostsData(userId);
		when(postDAO.getPosts()).thenReturn(posts);

		List<Post> returned = postServiceImpl.getPosts();

		verify(postDAO, times(1)).getPosts();

		verifyNoMoreInteractions(postDAO);
		assertEquals(posts.size(), returned.size());
		assertEquals(posts.get(0).getId(), returned.get(0).getId());
		assertEquals(posts.get(1).getId(), returned.get(1).getId());
		assertEquals(posts.get(0).getUser().getId(), returned.get(0).getUser().getId());
		assertEquals(posts.get(1).getUser().getId(), returned.get(1).getUser().getId());
	}

	private List<Post> getPostsData(int userId) {
		ArrayList<Post> list = new ArrayList<>();
		list.add(new Post(1, "Test", new User(userId, "Mike", "1000 N St", "mike@xyz.com")));
		list.add(new Post(2, "Test2", new User(userId, "Mike", "1000 N St", "mike@xyz.com")));
		return list;
	}
}
