package com.movie.rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.rental.exception.MovieRentalValidationException;
import com.movie.rental.model.Movie;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing Movies.
 */
@Service
@Slf4j // lombok
@Getter
@Setter
public class MovieService  extends AbstractService {

    @Autowired
    private MovieUpdatesService movieUpdatesService;

    /**
     * Decrease the Movie Stock by one
     * @param movie The {@link Movie} object
     */
    public void decreaseStock(final Movie movie) {
        final Integer stock = movie.getStock();

        if (stock > 0) {
            log.info("Decreasing this Movie stock by 1: {} - {}", movie.getId(),
                    movie.getTitle());

            final Integer newStock = stock - 1;
            movie.setStock(newStock);

            // If the stock is 0 then set the Movie as not available
            if (newStock.equals(0)) {
                movie.setAvailable(Boolean.FALSE);
            }

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

        final Integer oldStock = movie.getStock();

        // If the old stock was 0, then set the Movie as available
        if (oldStock.equals(0)) {
            movie.setAvailable(Boolean.TRUE);
        }

        movie.setStock(oldStock + 1);
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

}
