package com.movie.rental.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.movie.rental.exception.ResourceNotFoundException;
import com.movie.rental.model.Movie;
import com.movie.rental.model.Purchase;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.repository.RentalRepository;
import com.movie.rental.repository.UserRepository;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class for the services
 */
@Getter
@Setter
public class AbstractService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RentalRepository rentalRepository;

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
     * Check if a Rental object exists, if so, return it, else throw exception
     * @param rentalId The Rental Id
     * @return The Rental
     */
    public Rental checkIfRentalExists(final Long rentalId) {
        Rental rentalObj = null;

        final Optional<Rental> rental = rentalRepository.findById(rentalId);

        if (rental.isPresent()) {
            rentalObj = rental.get();
        } else {
            throw new ResourceNotFoundException("Rental not found");
        }

        return rentalObj;
    }

}
