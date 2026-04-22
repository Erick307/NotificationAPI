package com.ericksilva.notificationapi.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Long id,String email) {
        return Jwts
                .builder()
                .claim("userid",id)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 ))
                .signWith(getKey())
                .compact();
    }

    public Optional<String> parseToken(String token){
        try {
            String email = Jwts
                    .parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return Optional.of(email);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    public Optional<Long> getUserId(String token){
        try {
            Long id = Jwts
                    .parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("userid",Long.class);
            return Optional.of(id);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
