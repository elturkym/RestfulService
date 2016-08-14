package com.service;

import java.util.Collection;

import com.domain.Comment;

public interface CommentService {

	Comment getCommentById(int id);

	Collection<Comment> getCommentsByPostId(int postId);

}
