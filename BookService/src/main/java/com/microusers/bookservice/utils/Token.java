package com.microusers.bookservice.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class Token {

        public UUID decodeJWT(String jwt) throws JwtException {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("sd5745FAHFW").parseClaimsJws(jwt).getBody();

                System.out.println("jwt id: " + claims.getId());
                return UUID.fromString(claims.getId());
            } catch (ExpiredJwtException e) {
                throw new JwtException("session time out");
            }
        }


}

