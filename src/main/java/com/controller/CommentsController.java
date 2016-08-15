package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Comment;
import com.service.CommentService;

@RestController
public class CommentsController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/posts/{postId}/comments", method = RequestMethod.GET)
	public List<Comment> getCommetsByPost(@PathVariable int postId) {
		return commentService.getCommentsByPostId(postId);
	}
}