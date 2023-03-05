package ru.itmo.highload.stroroom.notificationsws.services;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itmo.highload.stroroom.notificationsws.dtos.TokenMessage;

import java.util.Optional;
import javax.crypto.spec.SecretKeySpec;


import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
@Slf4j
public class JwtHelper {
    @Value("${app.security.jwt.secret}")
    private String jwtSecret;
    private final SignatureAlgorithm sa = HS256;

    public Optional<String> getUsernameIfValid(String token) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), sa.getJcaName());
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(secretKeySpec)
                    .build()
                    .parseClaimsJws(token);
            return Optional.of(jws.getBody().getSubject());
        } catch (JwtException e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }
    public Optional<String> getUsernameIfValid(TokenMessage tokenMessage) {
        return getUsernameIfValid(tokenMessage.getToken());
    }

}
