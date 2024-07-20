package com.suvankar.blogapis.repositories;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.suvankar.blogapis.entity.Category;
import com.suvankar.blogapis.entity.Post;
import com.suvankar.blogapis.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	public List<Post> findByUser(User user); //Spring Data JPA generates the queries for you based on the method names
	public List<Post> findByCategory(Category category);  //Spring Data JPA generates the queries for you based on the method names 
	@Query("select p from Post p where p.title like :key")
	public List<Post> searchByTitle(@Param("key") String title);
	
}
