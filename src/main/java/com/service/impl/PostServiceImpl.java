package com.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.PostDAO;
import com.domain.Post;
import com.service.PostService;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;

	@Override
	public Post getPostById(int id) {
		return postDAO.getPostById(id);
	}

	@Override
	public Collection<Post> getPostsByUserId(int userId) {
		return postDAO.getPostsByUserId(userId);
	}

	@Override
	public Collection<Post> getPosts() {
		return postDAO.getPosts();
	}

}
