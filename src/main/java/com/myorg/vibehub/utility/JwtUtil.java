package com.myorg.vibehub.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
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
    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                //current time + 2 mins = expiration
                .expiration(new Date(System.currentTimeMillis() + AUTH_EXPIRATION))
                .signWith(KEY,Jwts.SIG.HS256)
                .compact();
    }

}
