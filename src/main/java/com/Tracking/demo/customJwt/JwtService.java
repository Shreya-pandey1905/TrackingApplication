package com.Tracking.demo.customJwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final Long expirationMs;

    public JwtService(@Value("${jwt.secret}") String secretKey,@Value ("${jwt.expiration-ms}") Long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));// converting string to secret key
        this.expirationMs = expirationMs;
    }

    /*
        payload:
        {
        "subject":"email",
        "role":"role",
        "issuedAt(iat)":"dateTime",
        "exp":expiryTime
        }
        * */
    public String generateToken(String  email, String role){
        Date now= new Date();
        return Jwts.builder()
                .subject(email)
                .claim("role",role)
                .issuedAt(now)
                .expiration(new Date(now.getTime()+expirationMs))
                .signWith(secretKey,Jwts.SIG.HS256)
                .compact();
    }

    public String extraSubject(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }
}
