package com.learnSphere.services;

import java.util.List;

import com.learnSphere.entity.Comments;

public interface CommentService {
	List<Comments> commentsList();
	
	String addComment(Comments comment);
}
