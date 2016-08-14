package com.dao;
import java.util.Collection;

import com.domain.User;

public interface UserDAO {

	User getUserById(int id);

	Collection<User> getUsers();

}
