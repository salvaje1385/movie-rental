package com.movie.rental.service;

import org.springframework.stereotype.Service;

import com.movie.rental.model.Movie;
import com.movie.rental.model.User;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.repository.UserRepository;
import com.movie.rental.service.dto.LikeDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing users.
 */
@Service
@Slf4j // lombok
public class UserService extends AbstractService {

    /**
     * Full constructor
     * @param userRepository An {@link UserRepository}
     * @param movieRepository A {@link MovieRepository}
     * @param purchaseRepository A {@link purchaseRepository}
     */
    public UserService(final UserRepository userRepository,
            final MovieRepository movieRepository,
            final PurchaseRepository purchaseRepository) {
        super(userRepository, movieRepository, purchaseRepository);
    }

    /**
     * Use this method when a User likes a Movie
     * @param likeDTO The LikeDTO
     * @return The User who liked the Movie
     */
    public User likeMovie(final LikeDTO likeDTO) {
        log.info("Like Movie: {}", likeDTO);

        final User userObj = checkIfUserExists(likeDTO.getUserId());
        final Movie movieObj = checkIfMovieExists(likeDTO.getMovieId());

        log.info("This User: {}-{} liked this movie: {}-{}", userObj.getId(),
                userObj.getName(), movieObj.getId(), movieObj.getTitle());

        movieObj.getUsersWhoLike().add(userObj);
        getMovieRepository().save(movieObj);

        return userObj;
    }
}
