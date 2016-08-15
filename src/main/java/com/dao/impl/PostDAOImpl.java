package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.PostDAO;
import com.domain.Post;

@Repository
public class PostDAOImpl implements PostDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Post getPostById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (Post) session.get(Post.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByUserId(int userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Post where user.id = ?");
		query.setInteger(0, userId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPosts() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Post");
		return query.list();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
