package com.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Post;
import com.service.PostService;

@RestController
public class PostsController {

	@Autowired
	private PostService postService;

	@RequestMapping(value = "/users/{userId}/posts", method = RequestMethod.GET)
	public Collection<Post> getPostsByUser(@PathVariable int userId) {
		return postService.getPostsByUserId(userId);
	}

	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public Collection<Post> getPost() {
		return postService.getPosts();
	}

	@RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
	public Post getPost(@PathVariable int id) {
		return postService.getPostById(id);
	}
}