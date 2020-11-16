package com.movie.rental.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.movie.rental.model.ERole;
import com.movie.rental.service.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * Abstract Controller
 */
@Slf4j
public class AbstractController {

    /**
     * Get the logged in User
     * @return A {@link UserDetailsImpl} object
     */
    public UserDetailsImpl getLoggedInUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = null;

        // If the User is not logged in 'getPrincipal()' returns an Anonymous user object
        if (auth.getPrincipal() instanceof UserDetailsImpl) {
            userDetails = (UserDetailsImpl) auth.getPrincipal();

            log.info("userDetails: {}-{}", userDetails.getId(), userDetails.getUsername());
            boolean isAdmin = false;
            for (final GrantedAuthority ga : auth.getAuthorities()) {
                if (ga.getAuthority().equals(ERole.ROLE_ADMIN.toString())) {
                    isAdmin = true;
                    break;
                }
            }

            userDetails.setAdmin(isAdmin);
        }

        return userDetails;
    }

}
