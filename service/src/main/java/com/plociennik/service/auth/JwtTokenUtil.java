package com.plociennik.service.auth;

import com.plociennik.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "your_secret_key"; // Replace with a secure key
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; // 5 hours in milliseconds

    // Generate a token for a user
    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole()) // You can add more claims if needed
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Validate the token
    public boolean validateToken(String token, UserEntity user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract a claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
