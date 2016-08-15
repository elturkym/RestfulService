package com.service;
import java.util.List;

import com.domain.Post;

public interface PostService {

	Post getPostById(int id);

	List<Post> getPostsByUserId(int userId);

	List<Post> getPosts();
}
