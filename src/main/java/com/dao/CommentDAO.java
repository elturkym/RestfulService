package com.dao;

import java.util.Collection;

import com.domain.Comment;

public interface CommentDAO {

	Comment getCommentById(int id);

	Collection<Comment> getCommentsByPostId(int postId);

}
