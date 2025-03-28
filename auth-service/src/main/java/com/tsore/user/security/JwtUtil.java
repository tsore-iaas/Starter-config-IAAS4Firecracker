package com.tsore.user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    public String generateToken(String email) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String extractEmail(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }

    public boolean validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWT.require(algorithm).build().verify(token);
        return true;
    }
}
