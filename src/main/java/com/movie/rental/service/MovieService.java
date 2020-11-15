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

    private final MovieUpdatesService movieUpdatesService;

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
            final RentalRepository rentalRepository,
            final MovieUpdatesService movieUpdatesService) {
        super(userRepository, movieRepository, purchaseRepository,
                rentalRepository);

        this.movieUpdatesService = movieUpdatesService;
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

    /**
     * Decrease the Movie Likes by one
     * @param movie The {@link Movie} object
     */
    public void decreaseLikes(final Movie movie) {
        final Integer likes = movie.getLikes() != null ? movie.getLikes() : 0;

        if (likes > 0) {
            log.info("Decreasing this Movie's Likes by 1: {} - {}", movie.getId(),
                    movie.getTitle());
            movie.setLikes(likes - 1);
            getMovieRepository().save(movie);
        } else {
            throw new MovieRentalValidationException("The Movie's likes can't be lower than 0");
        }
    }

    /**
     * Increase the Movie Likes by one
     * @param movie The {@link Movie} object
     */
    public void increaseLikes(final Movie movie) {
        log.info("Increasing this Movie's Likes by 1: {} - {}", movie.getId(),
                movie.getTitle());

        final Integer likes = movie.getLikes() != null ? movie.getLikes() : 0;
        movie.setLikes(likes + 1);
        getMovieRepository().save(movie);
    }

    /**
     * Update a Movie
     * @param id The Movie Id
     * @param movieRequest The new Movie parameters
     * @return The updated Movie
     */
    public Movie updateMovie(final Long id, final Movie movieRequest) {
        log.info("Update Movie: id: {}, movie: {}", id, movieRequest.getTitle());
        final Movie movie = checkIfMovieExists(id);
        final String oldTitle = movie.getTitle();
        final String oldRentalPrice = movie.getRentalPrice().toString();
        final String oldSalePrice = movie.getSalePrice().toString();

        movie.setTitle(movieRequest.getTitle());
        movie.setDescription(movieRequest.getDescription());
        movie.setStock(movieRequest.getStock());
        movie.setRentalPrice(movieRequest.getRentalPrice());
        movie.setSalePrice(movieRequest.getSalePrice());
        movie.setAvailable(movieRequest.getAvailable());
        getMovieRepository().save(movie);

        // Create a MovieUpdate register
        getMovieUpdatesService().createMovieUpdate(movie, oldTitle,
                oldRentalPrice, oldSalePrice);

        return movie;
    }

    /**
     * Getter for the MovieUpdates Service
     * @return The MovieUpdates Service
     */
    public MovieUpdatesService getMovieUpdatesService() {
        return this.movieUpdatesService;
    }

}
