package com.myorg.vibehub.utility;

import com.myorg.vibehub.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET;
    private final SecretKey KEY;

    //after how long token should expire?
    private final long AUTH_EXPIRATION;

    public JwtUtil (@Value("${jwt.secret}")String jwtSecret) {
        SECRET = jwtSecret;

        //Security Hashing Key For-
        //Convert SECRET to Bytes
        KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

        //AUTH_EXPIRATION time in miliseconds
        AUTH_EXPIRATION = 1000 * 60 * 2; //2 mins
    }
    //method to generate token on the basis of username as the unique identifier-
    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role",user.getRole())
                .claim("name", user.getName())
                .issuedAt(new Date())
                //current time + 2 mins = expiration
                .expiration(new Date(System.currentTimeMillis() + AUTH_EXPIRATION))
                .signWith(KEY,Jwts.SIG.HS256)
                .compact();
    }

    //Method to extract Username
    public String extractUsername(String token){
        //from claims, you extract subject = username
        return getClaims(token).getSubject();
    }

    public String extractRole(String token){
        //from claims, you extract subject = username
        return getClaims(token).get("role", String.class);
    }

    //Method to get Claims
    private Claims getClaims(String token){
     return Jwts.parser()
             .verifyWith(KEY)//verify token is correct for security purpose-checks expire too
             .build() //returns jwtparser
             .parseSignedClaims(token)
             .getPayload();//returns payload, also called claims
    }

    //these two methods are not needed as now, you can not get claim now until token is validated
    //Validate/match user from token and userdetails
    public boolean validateToken(String username, UserDetails userDetails, String token){
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        //checks is token is expired before current time.if true-expired,false-valid
       return getClaims(token).getExpiration().before(new Date());
    }

}
