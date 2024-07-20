package com.suvankar.blogapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suvankar.blogapis.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
	
}
