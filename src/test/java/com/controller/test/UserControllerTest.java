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

import com.controller.UserController;
import com.domain.User;
import com.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserControllerTest {

	@Configuration
	static class userControllerTestConfiguration {

		@Bean
		public UserService accountService() {
			return Mockito.mock(UserService.class);
		}

		@Bean
		public UserController userController() {
			return new UserController();
		}
	}

	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Autowired
	private UserController userController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
	}

	@Test
	public void testGetExistingUserById() throws Exception {
		User u = new User(1, "Mike", "1000 N St", "mike@xyz.com");

		when(userService.getUserById(1)).thenReturn(u);
		mockMvc.perform(get("/users/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.address", is("1000 N St"))).andExpect(jsonPath("$.name", is("Mike")))
				.andExpect(jsonPath("$.email", is("mike@xyz.com")));

		verify(userService, atLeastOnce()).getUserById(1);

		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testGetNotExistingUserById() throws Exception {
		when(userService.getUserById(1)).thenReturn(null);
		mockMvc.perform(get("/users/1"))
		.andExpect(status().isOk())
		.andReturn().equals(null);
		verify(userService, atLeastOnce()).getUserById(1);

		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testPutIsNoTAllowed() throws Exception {
		mockMvc.perform(put("/users/1")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testDeleteIsNoTAllowed() throws Exception {
		mockMvc.perform(delete("/users/1")).andExpect(status().isMethodNotAllowed());
		verifyNoMoreInteractions(userService);
	}
}
