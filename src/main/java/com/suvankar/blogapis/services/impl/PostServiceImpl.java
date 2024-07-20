package com.suvankar.blogapis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.suvankar.blogapis.entity.Category;
import com.suvankar.blogapis.entity.Post;
import com.suvankar.blogapis.entity.User;
import com.suvankar.blogapis.exceptions.ResultNotFoundException;
import com.suvankar.blogapis.payloads.PostDto;
import com.suvankar.blogapis.payloads.PostResponse;
import com.suvankar.blogapis.repositories.CategoryRepo;
import com.suvankar.blogapis.repositories.PostRepo;
import com.suvankar.blogapis.repositories.UserRepo;
import com.suvankar.blogapis.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResultNotFoundException("User", "user id", userId));
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResultNotFoundException("Category", "category id", categoryId ));
		
		Post p=this.modelMapper.map(postDto, Post.class);
		p.setImagename("default.png");
		
		p.setAddedDate(new Date());
		
		p.setCategory(category);
		p.setUser(user);
		
		Post newPost=this.postRepo.save(p);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()->new ResultNotFoundException("post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setImagename(postDto.getImageName());
		post.setContent(postDto.getContent());
		Post updatedPost= this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()->new ResultNotFoundException("post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else
			sort=Sort.by(sortBy).descending();
		
		Pageable p= PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		
		List<Post>allPosts=pagePost.getContent();
		
		List<PostDto> Postdtos= allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse =new PostResponse();
		postResponse.setContent(Postdtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}
	

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResultNotFoundException("post", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostBycatagory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResultNotFoundException("category", "categoryid", categoryId));
		List<Post> posts=this.postRepo.findByCategory(cat);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResultNotFoundException("user", "userid",userId));
		List<Post> posts=this.postRepo.findByUser(user);
		List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts= this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> allPosts=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPosts;
	}

	

}
