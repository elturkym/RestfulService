package com.dao;
import java.util.List;

import com.domain.User;

public interface UserDAO {

	User getUserById(int id);

	List<User> getUsers();

}
