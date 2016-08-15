package com.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
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

	@RequestMapping(value = "/users/{userId}/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Resource<Post>> getPostsByUser(@PathVariable int userId) {
		List<Post> posts = postService.getPostsByUserId(userId);
		return getPostsResourcesList(posts);
	}

	@RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Resource<Post>> getPost() {
		List<Post> posts = postService.getPosts();
		return getPostsResourcesList(posts);
	}

	@RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Resource<Post> getPost(@PathVariable int id) {
		Post post = postService.getPostById(id);
		if (post == null) {
			return null;
		}
		return getPostResource(post);
	}

	private List<Resource<Post>> getPostsResourcesList(List<Post> posts) {
		List<Resource<Post>> resources = new ArrayList<Resource<Post>>();
		for (Post post : posts) {
			resources.add(getPostResource(post));
		}
		return resources;
	}

	private Resource<Post> getPostResource(Post post) {
		Resource<Post> resource = new Resource(post);
		resource.add(linkTo(methodOn(CommentsController.class).getCommetsByPost(post.getId())).withRel("comments"));
		return resource;
	}
}