package com.suvankar.blogapis.payloads;

import java.util.HashSet;
import java.util.Set;

import com.suvankar.blogapis.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class UserDto {
	
	private int id;
	@NotEmpty
	@Size(min=4,message="User name must be min of 4 characters !!")
	private String name;
	
	@NotEmpty
	private String about;
	
	@NotEmpty
	@Size(min=3,max=10,message="Password must be minimum of 3 chars and maximum of 10 chars")
	private String password;
	
	@Email(message="email adress is not valid !!")
	private String email;
	
	private Set<RoleDto> roles=new HashSet<RoleDto>();
}
