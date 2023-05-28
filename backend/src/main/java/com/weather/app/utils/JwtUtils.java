package com.weather.app.utils;

import com.weather.app.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Jwt Authentication service
 *
 * @author raihan
 */
@Component
public class JwtUtils {
    /**
     * method for extracting username
     * @param token type String
     * @return string
     *
     * @author raihan
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * method for extracting claims
     * @param token type String
     * @param claimsResolver generic function of claims
     * @return genericType
     * @param <T> generic type
     * @author raihan
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * method for generating token
     * @param userDetails type UserDetails
     * @return String
     * @author raihan
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * method for generating token
     * @param extraClaims type map
     * @param userDetails UserDetails
     * @return String
     * @author raihan
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXIPIRATION_TIME))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Constants.SECURITY_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}