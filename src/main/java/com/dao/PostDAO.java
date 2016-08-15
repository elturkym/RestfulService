package com.dao;
import java.util.List;

import com.domain.Post;

public interface PostDAO {

	Post getPostById(int id);

	List<Post> getPostsByUserId(int userId);

	List<Post> getPosts();
}
