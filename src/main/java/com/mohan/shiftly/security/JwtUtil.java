package com.mohan.shiftly.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private String JWT_SECRECT="ADSUHKJDKJDFBKJBVKLFNBSVKJSFHKJSFH";
    private Long JWT_EXPIRATION=24*60*60*1000L;

    private Key getSiginKey(){
        return Keys.hmacShaKeyFor(JWT_SECRECT.getBytes());
    }
    public String generateJwtToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256,getSiginKey())
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSiginKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public boolean isValidToken(String token){
        try {
            extractAllClaims(token);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
