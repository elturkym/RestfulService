package com.service;

import java.util.Collection;

import com.domain.User;

public interface UserService {

	User getUserById(int id);

	Collection<User> getUsers();

}
