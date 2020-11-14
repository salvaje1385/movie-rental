package com.movie.rental.service;

import org.springframework.stereotype.Service;

import com.movie.rental.model.Movie;
import com.movie.rental.model.User;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.repository.RentalRepository;
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
     * @param rentalRepository A {@link rentalRepository}
     */
    public UserService(final UserRepository userRepository,
            final MovieRepository movieRepository,
            final PurchaseRepository purchaseRepository,
            final RentalRepository rentalRepository) {
        super(userRepository, movieRepository, purchaseRepository,
                rentalRepository);
    }

    /**
     * Use this method when a User likes/unlikes a Movie
     * @param likeDTO The LikeDTO
     * @return The User who liked the Movie
     */
    public User likeMovie(final LikeDTO likeDTO) {
        log.info("Like/unlike a Movie: {}", likeDTO);

        final User userObj = checkIfUserExists(likeDTO.getUserId());
        final Movie movieObj = checkIfMovieExists(likeDTO.getMovieId());

        log.info("This User: {}-{} {} this movie: {}-{}", userObj.getId(),
                userObj.getName(), likeDTO.getLike() ? "likes" : "unlikes",
                movieObj.getId(), movieObj.getTitle());

        if (likeDTO.getLike()) {
            // The User is liking the Movie
            movieObj.getUsersWhoLike().add(userObj);
        } else {
            // The User is unliking the Movie
            movieObj.getUsersWhoLike().remove(userObj);
        }
        getMovieRepository().save(movieObj);

        return userObj;
    }

}
