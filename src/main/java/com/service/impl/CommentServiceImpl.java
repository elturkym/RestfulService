package com.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CommentDAO;
import com.domain.Comment;
import com.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO commentDAO;

	@Override
	public Comment getCommentById(int id) {
		return commentDAO.getCommentById(id);
	}

	@Override
	public Collection<Comment> getCommentsByPostId(int postId) {
		return commentDAO.getCommentsByPostId(postId);
	}

}
