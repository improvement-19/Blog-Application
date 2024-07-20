package com.suvankar.blogapis.services;

import com.suvankar.blogapis.payloads.CommentDto;

public interface CommentService {
	public CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);
}
