package com.service.impl;

import java.util.List;

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
	public List<Comment> getCommentsByPostId(int postId) {
		return commentDAO.getCommentsByPostId(postId);
	}

	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

}
