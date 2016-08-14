package com.service;
import java.util.Collection;

import com.domain.Post;

public interface PostService {

	Post getPostById(int id);

	Collection<Post> getPostsByUserId(int userId);

	Collection<Post> getPosts();
}
