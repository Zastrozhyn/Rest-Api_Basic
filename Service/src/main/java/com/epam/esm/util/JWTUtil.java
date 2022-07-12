package com.epam.esm.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.subject}")
    private String subject;

    @Value("${jwt.expirationTime.minute}")
    private Long expirationTime;

    public String generateToken(String userName){
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(expirationTime).toInstant());
        return JWT.create()
                .withSubject(subject)
                .withClaim("userName", userName)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String validateTokenAndRetrieveClaim (String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                .withSubject("UserDetails")
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("userName").asString();
    }
}
