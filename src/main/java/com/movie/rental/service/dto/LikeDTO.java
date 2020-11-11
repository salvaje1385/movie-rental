package com.movie.rental.service.dto;

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
    private Long userId;

    /**
     * The {@link Movie} Id
     */
    private Long movieId;

}
