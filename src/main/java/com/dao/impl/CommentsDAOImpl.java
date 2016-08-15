package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.CommentDAO;
import com.domain.Comment;

@Repository
public class CommentsDAOImpl implements CommentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Comment getCommentById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (Comment) session.get(Comment.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsByPostId(int postId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select c from Comment c join c.post p where p.id = ?");
		query.setInteger(0, postId);
		List<Comment> comments = query.list();
		return comments;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
