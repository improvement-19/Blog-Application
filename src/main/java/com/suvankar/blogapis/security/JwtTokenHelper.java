package com.suvankar.blogapis.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Component
public class JwtTokenHelper {
	public static final long JWT_TOKEN_VALIDITY= 5*60*60 ;
	
	private String secret ="abcdefghijklmnopqrsTUVWXYZabcdefghijklmnopqrsTUVWXYZabcdefghijklmnopqrsTUVWXYZabcdefghijklmnopqrsTUVWXYZabcdefghijklmnopqrsTUVWXYZ";
	
	public String getUserNameFromToken (String token)
	{
		return getClaimFromToken(token,Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token,Function<Claims,T> claimsResolver)
	{
		final Claims claim=getAllClaimsFromToken(token);
		return claimsResolver.apply(claim);
	}
	
	//Function<Claims,T>: It is a functional interface (single abstract method),  A Function that accepts a Claims object and returns a value of type T . 
	
	//claimResolver is a parameter of type Function<Claims,T>
	
	//Claims::getSubject is a lambda expression that points to the getSubject method of the Claims class. This method retrieves the "subject" claim from the JWT, which typically holds the username.
	//T depends on the implementation ,Claims::getSubject is an implementation for the claimsResolver. It defines what the function should do with the Claims object: extract the "subject" claim(containing the username,return type string).
	
	
	private Claims getAllClaimsFromToken(String token)
	{
		 //return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		 return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
	//it is decoding actually.
	//The parserBuilder() method in Spring is used to create a new JwtParserBuilder object. This object can then be used to set the signing key and parse the JWT. The parseClaimsJws() method is used to parse the JWT and return a Jws object
	
	private Boolean isTokenExpired(String token)
	{
		final Date expiration=getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails userDetails)
	{
		Map<String,Object> claims=new HashMap<String, Object>();
		return doGenerateToken(claims,userDetails.getUsername());
	}
	
	private Key getSigningKey() {
		  byte[] keyBytes = Decoders.BASE64.decode(this.secret);
		  return Keys.hmacShaKeyFor(keyBytes);
		}
	
	 //while generating the token it is signed using HMAC SHA-512 algorithm
    //Combining the secret key with the token's header and payload (claims) through a hashing function (SHA-512).It processes the message and key through a series of mathematical operations to generate a fixed-size hash value (signature).
	//Generating a unique signature that acts like a fingerprint for the token.
    //header and payload is internally encoded by BASE64  encoder using JWT library .
    // Builder() creates a object and then using different methods things are set , compact() means URL safe string with 3 dot
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
                		
	}


    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
