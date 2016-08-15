package com.service;

import java.util.List;

import com.domain.Comment;

public interface CommentService {

	Comment getCommentById(int id);

	List<Comment> getCommentsByPostId(int postId);

}
