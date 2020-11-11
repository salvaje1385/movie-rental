package com.movie.rental.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.movie.rental.exception.ResourceNotFoundException;
import com.movie.rental.model.Movie;
import com.movie.rental.model.User;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.UserRepository;
import com.movie.rental.service.dto.LikeDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing users.
 */
@Service
@Slf4j // lombok
public class UserService {

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    public UserService(final UserRepository userRepository,
            final MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * Use this method when a User likes a Movie
     * @param likeDTO The LikeDTO
     * @return The User who liked the Movie
     */
    public User likeMovie(final LikeDTO likeDTO) {
        log.info("Like Movie: {}", likeDTO);

        User userObj = null;
        Movie movieObj = null;

        final Optional<User> user = userRepository.findById(likeDTO.getUserId());

        if (user.isPresent()) {
            userObj = user.get();
        } else {
            new ResourceNotFoundException("User not found");
        }

        final Optional<Movie> movie = movieRepository.findById(likeDTO.getMovieId());

        if (movie.isPresent()) {
            movieObj = movie.get();
        } else {
            new ResourceNotFoundException("Movie not found");
        }

        log.info("This User: {}-{} liked this movie: {}-{}", userObj.getId(),
                userObj.getName(), movieObj.getId(), movieObj.getTitle());

        movieObj.getUsersWhoLike().add(userObj);
        movieRepository.save(movieObj);

        return userObj;
    }
}
