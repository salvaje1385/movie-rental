package com.movie.rental.service.dto;

import javax.validation.constraints.NotNull;

import com.movie.rental.model.Movie;
import com.movie.rental.model.User;

import lombok.Data;

/**
 * A DTO holding Purchase data
 */
final @Data // lombok data annotation
public class PurchaseDTO {

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
     * The purchase's price
     */
    @NotNull(message="Please provide a Price")
    private Double price;

}
