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

import com.dao.UserDAO;
import com.domain.User;
import com.service.impl.UserServiceImpl;

public class UserServiceTest {

	private UserServiceImpl userService;
	private UserDAO userDAO;

	@Before
	public void setUp() throws Exception {
		userDAO = Mockito.mock(UserDAO.class);
		userService = new UserServiceImpl();
		userService.setUserDAO(userDAO);
	}

	@Test
	public void getAnExistingUser() throws Exception {
		int id = 1;
		User u = new User(id, "Mike", "1000 N St", "mike@xyz.com");

		when(userDAO.getUserById(id)).thenReturn(u);
		User returned = userService.getUserById(id);

		verify(userDAO, times(1)).getUserById(id);
		verifyNoMoreInteractions(userDAO);

		assertEquals(u.getId(), returned.getId());

	}

	@Test
	public void getNotExistingUser() throws Exception {
		int id = 10;

		when(userDAO.getUserById(id)).thenReturn(null);
		User returned = userService.getUserById(id);

		verify(userDAO, times(1)).getUserById(id);
		verifyNoMoreInteractions(userDAO);

		assertNull(returned);
	}

	@Test
	public void getAllExistingUser() throws Exception {

		ArrayList<User> list = new ArrayList<>();
		list.add(new User(1, "Mike", "1000 N St", "mike@xyz.com"));
		list.add(new User(2, "Ali", "1000 N St", "mike@xyz.com"));

		when(userDAO.getUsers()).thenReturn(list);

		List<User> returned = userService.getUsers();

		verify(userDAO, times(1)).getUsers();

		verifyNoMoreInteractions(userDAO);
		assertEquals(list.size(), returned.size());
		assertEquals(list.get(0).getId(), returned.get(0).getId());
		assertEquals(list.get(1).getId(), returned.get(1).getId());
	}

	@Test
	public void testNoUsersExist() throws Exception {

		ArrayList<User> list = new ArrayList<>();
		when(userDAO.getUsers()).thenReturn(list);

		List<User> returned = userService.getUsers();

		verify(userDAO, times(1)).getUsers();

		verifyNoMoreInteractions(userDAO);
		assertEquals(list.size(), returned.size());
		;
	}
}
