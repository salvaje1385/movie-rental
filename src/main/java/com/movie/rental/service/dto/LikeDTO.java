package com.movie.rental.service.dto;

import javax.validation.constraints.NotNull;

import com.movie.rental.model.Movie;
import com.movie.rental.model.User;

import lombok.Data;

/**
 * A DTO representing a User like to a Movie
 */
final @Data // lombok data annotation
public class LikeDTO {

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
     * If true the User is liking the movie, if false then unlikes it
     */
    @NotNull(message="Please provide a like parameter")
    private Boolean like;

}
