package com.movie.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Movie Rental Validation Exception
 */
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MovieRentalValidationException extends RuntimeException {

    public MovieRentalValidationException(final String message) {
        super(message);
    }

    public MovieRentalValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
