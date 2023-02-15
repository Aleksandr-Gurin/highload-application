package com.example.reactiveauthendpointaggregator.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Base64;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenService {
    private final ReactiveUserDetailsService userDetailsServiceImp;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }


    public Mono<Authentication> getAuthentication(String token) {
        return userDetailsServiceImp.findByUsername(getUsername(token))
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, "",
                        userDetails.getAuthorities()));

    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

}
