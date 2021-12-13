package com.tech081.authjwt.controller;

import com.tech081.authjwt.application.TokenUseCase;
import com.tech081.authjwt.controller.dto.AuthDTO;
import com.tech081.authjwt.controller.dto.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenUseCase tokenUseCase;

    public AuthController(AuthenticationManager authenticationManager, TokenUseCase tokenUseCase) {
        this.authenticationManager = authenticationManager;
        this.tokenUseCase = tokenUseCase;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody @Valid AuthDTO authDTO) {
        try {
            final var auth = new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword());
            final var authenticate = authenticationManager.authenticate(auth);
            final var token = tokenUseCase.generate(authenticate);
            return ResponseEntity.ok().body(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException authenticationException) {
            return ResponseEntity.badRequest().build();
        }
    }
}
