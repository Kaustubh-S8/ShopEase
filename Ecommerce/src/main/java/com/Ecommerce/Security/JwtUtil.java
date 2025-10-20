package com.Ecommerce.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
@Value("${jwt.secret}")
private String secret;


@Value("${jwt.expiration-ms}")
private long expirationMs;


public String generateToken(String subject) {
Date now = new Date();
Date expiry = new Date(now.getTime() + expirationMs);
SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

return Jwts.builder()
    .setSubject(subject)
    .setIssuedAt(now)
    .setExpiration(expiry)
    .signWith(key) // JJWT will choose the appropriate algorithm
    .compact();
//
//return Jwts.builder()
//.setSubject(subject)
//.setIssuedAt(now)
//.setExpiration(expiry)
//.signWith(SignatureAlgorithm.HS256, secret)
//.compact();
}


public String getUsername(String token) {
return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
}


public boolean validate(String token) {
try {
Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
return true;
} catch (JwtException | IllegalArgumentException e) {
return false;
}
}
}