package com.movie.rental.service;

import java.util.Optional;

import com.movie.rental.exception.ResourceNotFoundException;
import com.movie.rental.model.Movie;
import com.movie.rental.model.Purchase;
import com.movie.rental.model.User;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.repository.UserRepository;

public class AbstractService {

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private final PurchaseRepository purchaseRepository;

    public AbstractService(final UserRepository userRepository,
            final MovieRepository movieRepository,
            final PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.purchaseRepository = purchaseRepository;
    }

    /**
     * Check if an User object exists, if so, return it, else throw exception
     * @param userId The User Id
     * @return The User
     */
    public User checkIfUserExists(final Long userId) {
        User userObj = null;

        final Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            userObj = user.get();
        } else {
            throw new ResourceNotFoundException("User not found");
        }

        return userObj;
    }

    /**
     * Check if a Movie object exists, if so, return it, else throw exception
     * @param movieId The Movie Id
     * @return The Movie
     */
    public Movie checkIfMovieExists(final Long movieId) {
        Movie movieObj = null;

        final Optional<Movie> movie = movieRepository.findById(movieId);

        if (movie.isPresent()) {
            movieObj = movie.get();
        } else {
            throw new ResourceNotFoundException("Movie not found");
        }

        return movieObj;
    }

    /**
     * Check if a Purchase object exists, if so, return it, else throw exception
     * @param purchaseId The Purchase Id
     * @return The Purchase
     */
    public Purchase checkIfPurchaseExists(final Long purchaseId) {
        Purchase purchaseObj = null;

        final Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);

        if (purchase.isPresent()) {
            purchaseObj = purchase.get();
        } else {
            throw new ResourceNotFoundException("Purchase not found");
        }

        return purchaseObj;
    }

    /**
     * Getter for the {@link UserRepository}
     * @return The {@link UserRepository}
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * Getter for the {@link MovieRepository}
     * @return The {@link MovieRepository}
     */
    public MovieRepository getMovieRepository() {
        return movieRepository;
    }

    /**
     * Getter for the {@link PurchaseRepository}
     * @return The {@link PurchaseRepository}
     */
    public PurchaseRepository getPurchaseRepository() {
        return purchaseRepository;
    }

}
