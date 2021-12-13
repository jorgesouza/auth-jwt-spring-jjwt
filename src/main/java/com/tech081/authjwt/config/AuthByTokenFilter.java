package com.tech081.authjwt.config;

import com.tech081.authjwt.application.TokenUseCase;
import com.tech081.authjwt.data.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthByTokenFilter extends OncePerRequestFilter {

    private final TokenUseCase tokenUseCase;
    private final UserRepository userRepository;

    public AuthByTokenFilter(TokenUseCase tokenUseCase, UserRepository userRepository) {
        this.tokenUseCase = tokenUseCase;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final var token = retrieveToken(request);
        if (tokenUseCase.isValid(token)) {
            this.authenticateUser(token);
        }

        filterChain.doFilter(request, response); // segue o fluxo da requisição
    }

    private String retrieveToken(HttpServletRequest request) {
        final var token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }

    private void authenticateUser(String token) {
        final var userId = tokenUseCase.getUserId(token);
        final var user = userRepository.findById(userId);
        if (user.isPresent()) {
            final var userAuthenticate = user.get();
            final var authentication = new UsernamePasswordAuthenticationToken(userAuthenticate, null, userAuthenticate.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new EmptyResultDataAccessException("User not found", 1);
        }
    }
}
