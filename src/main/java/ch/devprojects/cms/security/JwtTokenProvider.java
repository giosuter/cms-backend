package ch.devprojects.cms.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Component for handling JWT token creation and validation.
 */
@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "mySuperSecretKeyForJwtAuthenticationMySuperSecretKeyForJwt";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private final SecretKey key;

    // Constructor initializes the signing key
    public JwtTokenProvider() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generates a JWT token for the authenticated user
    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256) // Correctly using HS256 for signing
                .compact();
    }

    // Extracts username from the JWT token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token) // Use parseSignedClaims for parsing
                .getPayload()
                .getSubject();
    }

    // Validates the JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token); // Validate the token
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;  // Token is invalid
        }
    }
}
