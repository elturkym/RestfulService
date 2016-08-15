package com.controller.test;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.controller.PostsController;
import com.domain.Post;
import com.domain.User;
import com.service.PostService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PostsControllerTest {

	@Configuration
	static class postControllerTestConfiguration {

		@Bean
		public PostService postService() {
			return Mockito.mock(PostService.class);
		}

		@Bean
		public PostsController postsController() {
			return new PostsController();
		}
	}
	
	private MockMvc mockMvc;

	@Autowired
	private PostService postService;

	@Autowired
	private PostsController postsController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(this.postsController).build();
	}

	@Test
	public void testGetPostsByUserId() throws Exception {
		int userId = 1;
		List<Post> posts = getPostsData(userId);

		when(postService.getPostsByUserId(userId)).thenReturn(posts);
		mockMvc.perform(get("/users/" + userId + "/posts")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].user.id", is(userId))).andExpect(jsonPath("$[1].user.id", is(userId)));
		verify(postService, atLeastOnce()).getPostsByUserId(userId);

		verifyNoMoreInteractions(postService);
	}

	@Test
	public void testGetPosts() throws Exception {
		int userId = 1;
		List<Post> posts = getPostsData(userId);

		when(postService.getPosts()).thenReturn(posts);
		mockMvc.perform(get("/posts")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].user.id", is(userId)))
				.andExpect(jsonPath("$[1].user.id", is(userId)));
		verify(postService, atLeastOnce()).getPosts();

		verifyNoMoreInteractions(postService);
	}

	private List<Post> getPostsData(int userId) {
		ArrayList<Post> list = new ArrayList<>();
		list.add(new Post(1, "Test", new User(userId, "Mike", "1000 N St", "mike@xyz.com")));
		list.add(new Post(2, "Test2", new User(userId, "Mike", "1000 N St", "mike@xyz.com")));
		return list;
	}

	@Test
	public void testGetEmptyPostsByUserId() throws Exception {
		int userId = 1;
		when(postService.getPostsByUserId(userId)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/users/" + userId + "/posts")).andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());
		
		verify(postService, atLeastOnce()).getPostsByUserId(userId);
		verifyNoMoreInteractions(postService);
	}
	
	@Test
	public void testGetPostByUserId() throws Exception {
		int postId = 1;
		Post p = new  Post();
		p.setId(1);
		
		when(postService.getPostById(postId)).thenReturn(p);
		mockMvc.perform(get("/posts/" + postId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(postId)));
		
		verify(postService, atLeastOnce()).getPostById(postId);
		verifyNoMoreInteractions(postService);
	}

	@Test
	public void testPutIsNoTAllowed() throws Exception {
		mockMvc.perform(put("/posts")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(postService);
		
		mockMvc.perform(put("/posts/1")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(postService);
		
		mockMvc.perform(put("/users/1/posts")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(postService);
	}

	@Test
	public void testDeleteIsNoTAllowed() throws Exception {
		mockMvc.perform(delete("/posts")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(postService);
		
		mockMvc.perform(delete("/posts/1")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(postService);
		
		mockMvc.perform(delete("/users/1/posts")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(postService);
	}

}
