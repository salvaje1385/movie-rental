package com.movie.rental.security.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Message Response payload
 */
@Getter
@Setter
public class MessageResponse {

    private String message;

    public MessageResponse(final String message) {
        this.message = message;
      }

}
