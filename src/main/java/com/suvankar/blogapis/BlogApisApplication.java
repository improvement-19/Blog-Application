package com.suvankar.blogapis;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.suvankar.blogapis.config.AppConstants;
import com.suvankar.blogapis.entity.Role;
import com.suvankar.blogapis.repositories.RoleRepo;

@SpringBootApplication
public class BlogApisApplication implements CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelmapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role=new Role();
			role.setId(AppConstants.ROLE_ADMIN);
			role.setName("ROLE_ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstants.ROLE_NORMAL);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles= List.of(role,role1);
			List<Role> results = this.roleRepo.saveAll(roles);
			results.forEach(r->{System.out.println(r.getName());});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(this.passwordEncoder.encode("abcde"));
	}
}
