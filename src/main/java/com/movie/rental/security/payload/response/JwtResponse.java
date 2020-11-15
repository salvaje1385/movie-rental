package com.movie.rental.security.payload.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * JWT Response payload
 */
@Getter
@Setter
public class JwtResponse {

    private String accessToken;

    private String tokenType = "Bearer";

    private Long id;

    private String username;

    private String email;

    private final List<String> roles;

    public JwtResponse(final String accessToken, final Long id, final String username,
            final String email, final List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}
