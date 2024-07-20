package com.suvankar.blogapis.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suvankar.blogapis.entity.Comment;
import com.suvankar.blogapis.entity.Post;
import com.suvankar.blogapis.exceptions.ResultNotFoundException;
import com.suvankar.blogapis.payloads.CommentDto;
import com.suvankar.blogapis.repositories.CommentRepo;
import com.suvankar.blogapis.repositories.PostRepo;
import com.suvankar.blogapis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResultNotFoundException("post", "post Id", postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment=this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResultNotFoundException("comment", "comment id", commentId));
		this.commentRepo.delete(com);
	}

}
