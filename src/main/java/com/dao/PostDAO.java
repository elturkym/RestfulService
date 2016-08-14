package com.dao;
import java.util.Collection;

import com.domain.Post;

public interface PostDAO {

	Post getPostById(int id);

	Collection<Post> getPostsByUserId(int userId);

	Collection<Post> getPosts();
}
