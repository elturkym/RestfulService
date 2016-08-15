package com.controller.test;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.controller.CommentsController;
import com.domain.Comment;
import com.domain.Post;
import com.domain.User;
import com.service.CommentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CommentsControllerTest {

	@Configuration
	static class CommentControllerTestConfiguration {

		@Bean
		public CommentService commentService() {
			return Mockito.mock(CommentService.class);
		}

		@Bean
		public CommentsController commentsController() {
			return new CommentsController();
		}
	}

	private MockMvc mockMvc;

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentsController commentsController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(this.commentsController).build();
	}

	@Test
	public void testGetAllCommentsByPostId() throws Exception {
		int postId = 1;
		ArrayList<Comment> comments = new ArrayList<>();
		Post p = new Post(postId, "Test", new User(1, "Mike", "1000 N St", "mike@xyz.com"));
		comments.add(new Comment(1, "comment1", p));
		comments.add(new Comment(2, "comment2", p));

		when(commentService.getCommentsByPostId(postId)).thenReturn(comments);
		mockMvc.perform(get("/posts/" + postId + "/comments")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].post.id", is(postId)))
				.andExpect(jsonPath("$[1].post.id", is(postId)));
		verify(commentService, atLeastOnce()).getCommentsByPostId(postId);

		verifyNoMoreInteractions(commentService);
	}

	@Test
	public void testGetEmptyCommentsByPostId() throws Exception {
		int postId = 1;
		when(commentService.getCommentsByPostId(postId)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/posts/" + postId + "/comments")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$").isEmpty());

		verify(commentService, atLeastOnce()).getCommentsByPostId(postId);
		verifyNoMoreInteractions(commentService);
	}

	@Test
	public void testPutIsNoTAllowed() throws Exception {
		mockMvc.perform(put("/posts/1/comments")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(commentService);
	}

	@Test
	public void testDeleteIsNoTAllowed() throws Exception {
		mockMvc.perform(delete("/posts/1/comments")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(commentService);
	}

}
