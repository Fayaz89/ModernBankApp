package com.pack.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JWTTokenParser {

    @Value("${jwt.secret}")
    private String secret;

    public Map<String, Object> parseToken(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(secret)
                            .parseClaimsJws(token)
                            .getBody();

        return claims;
    }
}
