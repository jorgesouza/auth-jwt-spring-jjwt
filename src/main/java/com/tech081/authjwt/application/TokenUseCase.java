package com.tech081.authjwt.application;

import com.tech081.authjwt.data.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenUseCase {

    @Value("${api.jwt.secret}")
    private String secret;

    @Value("${api.jwt.expiration}")
    private String expiration;

    public String generate(Authentication authentication) {
        final var user = (User) authentication.getPrincipal();
        final var today = new Date();
        final var expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("tech081") // gerador do token
                .setHeaderParam("typ", "JWT")
                .claim("name", user.getName())
                .claim("roles", user.getAuthorities())
                .setSubject(user.getId().toString()) // usuario autenticado dono do token
                .setIssuedAt(today) // data da geracao do token
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret) // algoritmo de criptografia e senha da aplicação
                .compact(); // compacta e transforma tudo em uma string
    }

    public boolean isValid(String token) {
        try {
            final var claimsJws = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);

            return claimsJws != null;
        } catch (Exception exception) {
            return false;
        }
    }

    public Long getUserId(String token) {
        final var claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }
}
