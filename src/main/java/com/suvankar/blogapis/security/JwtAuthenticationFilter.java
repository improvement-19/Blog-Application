package com.suvankar.blogapis.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken=request.getHeader("Authorization");
		//header e Authorization : token evabe thake + request type thake get,post
		//Bearer 26474yn8
		
		System.out.println(requestToken);
		String username=null;
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			token=requestToken.substring(7);
			try {
			username=this.jwtTokenHelper.getUserNameFromToken(token);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("Unable to get JWT token");
			}
			catch(ExpiredJwtException e)
			{
				System.out.println("Token has expired");
			}
			catch(MalformedJwtException e)
			{
				System.out.println("inavalid jwt token");
			}
		}
		else
		{
			System.out.println("Jwt token does not starts with Bearer");
		}
		//once we get the token 
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetail=this.userDetailService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userDetail))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else
			{
				System.out.println("Invalid Jwt token");
			}
		}
		else
		{
			System.out.println("Username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	}

}
