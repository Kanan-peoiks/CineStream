package com.example.cinestream.auth;

import com.example.cinestream.auth.dto.AuthPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthGraphQLController {

    private final AuthService authService;

    @MutationMapping
    public AuthPayload register(@Argument String username, @Argument String password) {
        return authService.register(username, password);
    }

    @MutationMapping
    public AuthPayload login(@Argument String username, @Argument String password) {
        return authService.login(username, password);
    }
}