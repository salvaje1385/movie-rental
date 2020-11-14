package com.movie.rental.service;

import org.springframework.stereotype.Service;

import com.movie.rental.exception.MovieRentalValidationException;
import com.movie.rental.model.Movie;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.repository.RentalRepository;
import com.movie.rental.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing Movies.
 */
@Service
@Slf4j // lombok
public class MovieService  extends AbstractService {

    /**
     * Full constructor
     * @param userRepository An {@link UserRepository}
     * @param movieRepository A {@link MovieRepository}
     * @param purchaseRepository A {@link PurchaseRepository}
     * @param rentalRepository A {@link RentalRepository}
     */
    public MovieService(final UserRepository userRepository,
            final MovieRepository movieRepository,
            final PurchaseRepository purchaseRepository,
            final RentalRepository rentalRepository) {
        super(userRepository, movieRepository, purchaseRepository,
                rentalRepository);
    }

    /**
     * Decrease the Movie Stock by one
     * @param movie The {@link Movie} object
     */
    public void decreaseStock(final Movie movie) {
        final Integer stock = movie.getStock();

        if (stock > 0) {
            log.info("Decreasing this Movie stock by 1: {} - {}", movie.getId(),
                    movie.getTitle());
            movie.setStock(stock - 1);
            getMovieRepository().save(movie);
        } else {
            throw new MovieRentalValidationException("The Movie stock can't be lower than 0");
        }
    }

    /**
     * Increase the Movie Stock by one
     * @param movie The {@link Movie} object
     */
    public void increaseStock(final Movie movie) {
        log.info("Increasing this Movie stock by 1: {} - {}", movie.getId(),
                movie.getTitle());
        movie.setStock(movie.getStock() + 1);
        getMovieRepository().save(movie);
    }

}
