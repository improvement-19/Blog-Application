package com.suvankar.blogapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.suvankar.blogapis.security.CustomUserDetailService;
import com.suvankar.blogapis.security.JwtAuthenticationEntryPoint;
import com.suvankar.blogapis.security.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalAuthentication
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	
	public static final String[] PUBLIC_URL = {"/api/v1/auth/**","/v3/api-docs","/swagger-ui/**","/swagger-ui.html","/swagger-resources/**"};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//		.csrf((csrf)->csrf.disable())
//		.cors((cors)->cors.disable())
//		.authorizeHttpRequests((authorizehhtp)->authorizehhtp.requestMatchers(PUBLIC_URLS).permitAll())
//		.anyRequest().authenticated().and()
//		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
//		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
		.csrf((csrf)->csrf.disable())
		.cors((cors)->cors.disable())
		.authorizeHttpRequests(auth->auth.requestMatchers(PUBLIC_URL).permitAll().requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated())
		
		.exceptionHandling((e)->e.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
		.sessionManagement((ses)->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // This setup allows for custom authentication using JWTs before falling back to traditional username/password authentication, 
		http.authenticationProvider(daoAuthenticationProvider());  //to include daoauthentication in securityfilterchain
	
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	//DaoAuthenticationProvider retrieves user details from userdetailservice
	//userdetailservice is used for fetching data not other class because it is made for authentication purpose and it fetch by username.
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
}
