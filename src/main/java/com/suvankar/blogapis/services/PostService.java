package com.suvankar.blogapis.services;

import java.util.List;

import com.suvankar.blogapis.entity.Post;
import com.suvankar.blogapis.payloads.PostDto;
import com.suvankar.blogapis.payloads.PostResponse;

public interface PostService {
	//create
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//update
	public PostDto updatePost(PostDto postDto,Integer postId);
	//delete
	public void deletePost(Integer postId);
	//getAll 
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//getbyid
	public PostDto getPostById(Integer postId);
	//get All post by category 
	public List<PostDto> getPostBycatagory(Integer categoryId);
	//get all post by user
	public List<PostDto>getPostByUser(Integer userId);
	//search post
	public List<PostDto>searchPost(String keyword);
	
}
