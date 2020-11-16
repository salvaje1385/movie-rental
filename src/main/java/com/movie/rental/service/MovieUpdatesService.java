package com.movie.rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.rental.model.Movie;
import com.movie.rental.model.MovieUpdates;
import com.movie.rental.model.MovieUpdates.UpdateType;
import com.movie.rental.repository.MovieUpdatesRepository;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing Movie Updates.
 */
@Service
@Slf4j // lombok
@Getter
@Setter
public class MovieUpdatesService extends AbstractService {

    @Autowired
    private MovieUpdatesRepository movieUpdatesRepository;

    /**
     * Create a MovieUpdate register
     * @param newMovie The new Movie
     * @param oldMovie The old Movie
     */
    public void createMovieUpdate(final Movie newMovie, final String oldTitle,
            final String oldRentalPrice, final String oldSalePrice) {

        if (!newMovie.getTitle().equals(oldTitle)) {
            saveMovieUpdate(newMovie, oldTitle, newMovie.getTitle(), UpdateType.TITLE_UPDATE);
        }

        if (!newMovie.getRentalPrice().toString().equals(oldRentalPrice)) {
            saveMovieUpdate(newMovie, oldRentalPrice, newMovie.getRentalPrice().toString(),
                    UpdateType.RENTAL_PRICE_UPDATE);
        }

        if (!newMovie.getSalePrice().toString().equals(oldSalePrice)) {
            saveMovieUpdate(newMovie, oldSalePrice, newMovie.getSalePrice().toString(),
                    UpdateType.SALE_PRICE_UPDATE);
        }
    }

    /**
     * Save a MovieUpdate register
     * @param movie The Movie
     * @param oldValue The property's old value
     * @param newValue The property's new value
     * @param updateType The Update Type
     */
    private void saveMovieUpdate(final Movie movie, final String oldValue,
            final String newValue, final UpdateType updateType) {

        log.info("Movie's {} update: old value: {}, new value: {}",
                updateType.toString(), oldValue, newValue);

        final MovieUpdates movieUpdate = new MovieUpdates();
        movieUpdate.setMovie(movie);
        movieUpdate.setUpdateType(updateType);
        movieUpdate.setOldValue(oldValue);
        movieUpdate.setNewValue(newValue);
        getMovieUpdatesRepository().save(movieUpdate);
    }

}
