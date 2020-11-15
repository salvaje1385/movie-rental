package com.movie.rental.service;

import org.springframework.stereotype.Service;

import com.movie.rental.model.Movie;
import com.movie.rental.model.Purchase;
import com.movie.rental.model.User;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.repository.RentalRepository;
import com.movie.rental.repository.UserRepository;
import com.movie.rental.service.dto.PurchaseDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing purchases.
 */
@Service
@Slf4j // lombok
public class PurchaseService extends AbstractService {

    private final MovieService movieService;

    /**
     * Full constructor
     * @param userRepository An {@link UserRepository}
     * @param movieRepository A {@link MovieRepository}
     * @param purchaseRepository A {@link PurchaseRepository}
     * @param rentalRepository A {@link RentalRepository}
     * @param movieService A {@link MovieService}
     */
    public PurchaseService(final UserRepository userRepository,
            final MovieRepository movieRepository,
            final PurchaseRepository purchaseRepository,
            final RentalRepository rentalRepository,
            final MovieService movieService) {
        super(userRepository, movieRepository, purchaseRepository,
                rentalRepository);

        this.movieService = movieService;
    }

    /**
     * Save or update a Purchase
     * @param purchaseDTO The PurchaseDTO
     * @return The Purchase created
     */
    public Purchase saveOrUpdatePurchase(final PurchaseDTO purchaseDTO) {
        log.info("saveOrUpdatePurchase: {}", purchaseDTO);

        final User user = checkIfUserExists(purchaseDTO.getUserId());
        final Movie movie = checkIfMovieExists(purchaseDTO.getMovieId());
        Movie oldMovie = null;
        boolean creatingPurchase = false;

        final Purchase purchase;
        if (purchaseDTO.getId() != null) {
            // Updating
            purchase = checkIfPurchaseExists(purchaseDTO.getId());
            oldMovie = purchase.getMovie();

            // If updating we take the old price
            purchase.setPrice(purchase.getPrice());
        } else {
            // Creating
            purchase = new Purchase();

            // The Price is only assigned when creating
            purchase.setPrice(movie.getSalePrice());

            creatingPurchase = true;
        }

        // Update the Movie stock
        updateMovieStock(creatingPurchase, movie, oldMovie);

        log.info("This User: {}-{} purchased this movie: {}-{} for: {}", user.getId(),
                user.getUsername(), movie.getId(), movie.getTitle(), purchase.getPrice());

        purchase.setUser(user);
        purchase.setMovie(movie);

        getPurchaseRepository().save(purchase);

        return purchase;
    }

    /**
     * Update the Movie stock
     * @param creatingPurchase True if we're creating a Purchase, false if we're updating it
     * @param movie The Movie to set
     * @param oldMovie The previous Movie
     */
    private void updateMovieStock(final boolean creatingPurchase,
            final Movie movie, final Movie oldMovie) {
        if (creatingPurchase) {
            // Decrease the Movie stock by one
            getMovieService().decreaseStock(movie);

        // If the Movie changes then update every Movie stock
        } else if (!movie.getId().equals(oldMovie.getId())) {
            getMovieService().increaseStock(oldMovie);
            getMovieService().decreaseStock(movie);
        }
    }

    /**
     * Delete a Purchase
     * @param purchaseId The Purchase Id
     */
    public void deletePurchase(final Long purchaseId) {
        log.info("Delete Purchase: {}", purchaseId);
        final Purchase purchase = checkIfPurchaseExists(purchaseId);

        // Increase the Movie stock by one
        getMovieService().increaseStock(purchase.getMovie());

        getPurchaseRepository().delete(purchase);
    }

    /**
     * Getter for the {@link MovieService}
     * @return The {@link MovieService}
     */
    public MovieService getMovieService() {
        return this.movieService;
    }
}
