package com.suvankar.blogapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.suvankar.blogapis.config.AppConstants;
import com.suvankar.blogapis.payloads.ApiResponse;
import com.suvankar.blogapis.payloads.PostDto;
import com.suvankar.blogapis.payloads.PostResponse;
import com.suvankar.blogapis.services.FileService;
import com.suvankar.blogapis.services.PostService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		PostDto createdPost= this.postService.createPost(postDto,userId , categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
	{
		List<PostDto> postDtos=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> postDtos=this.postService.getPostBycatagory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	//get all posts 
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false )String sortDir )
	{
		PostResponse postResponse= this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getAllPost(@PathVariable Integer postId)
	{
		PostDto postdto= this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postdto,HttpStatus.OK);
	}
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ApiResponse("post is successfully deleted",true);
	}
	//updatePost
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		PostDto updatedPost= this.postService.updatePost(postDto,postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords")String keywords)
	{
		List<PostDto> result= this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//post image upload 
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam MultipartFile image,@PathVariable Integer postId ) throws IOException
	{
		PostDto postDto= this.postService.getPostById(postId);
		String fileName= this.fileService.uploadImage(path, image);
		
	
		postDto.setImageName(fileName);
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	//image serve 
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response ) throws IOException
	{
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}