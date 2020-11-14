package com.movie.rental.service.dto;

import javax.validation.constraints.NotNull;

import com.movie.rental.model.Movie;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;

import lombok.Data;

/**
 * A DTO holding Rental data
 */
final @Data // lombok data annotation
public class RentalDTO {

    /**
     * The {@link Rental} Id
     */
    private Long id;

    /**
     * The {@link User} Id
     */
    @NotNull(message="Please provide an User Id")
    private Long userId;

    /**
     * The {@link Movie} Id
     */
    @NotNull(message="Please provide a Movie Id")
    private Long movieId;

    /**
     * The date when the Rental expires
     */
    @NotNull(message="Please provide a Due Date")
    private Long dueDate;

    /**
     * The rental's penalty if the User returns the Movie after the Due Date
     */
    private Double penalty;

    /**
     * The date when the Rented Movie was returned
     */
    private Long returnDate;
}
