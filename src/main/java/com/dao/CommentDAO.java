package com.dao;

import java.util.List;

import com.domain.Comment;

public interface CommentDAO {

	Comment getCommentById(int id);

	List<Comment> getCommentsByPostId(int postId);

}
