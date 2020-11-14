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

    /**
     * Full constructor
     * @param userRepository An {@link UserRepository}
     * @param movieRepository A {@link MovieRepository}
     * @param purchaseRepository A {@link purchaseRepository}
     * @param rentalRepository A {@link rentalRepository}
     */
    public PurchaseService(final UserRepository userRepository,
            final MovieRepository movieRepository,
            final PurchaseRepository purchaseRepository,
            final RentalRepository rentalRepository) {
        super(userRepository, movieRepository, purchaseRepository,
                rentalRepository);
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

        final Purchase purchase;
        if (purchaseDTO.getId() != null) {
            // Updating
            purchase = checkIfPurchaseExists(purchaseDTO.getId());
            // If updating we take the old price
            purchase.setPrice(purchase.getPrice());
        } else {
            // Creating
            purchase = new Purchase();
            // The Price is only assigned when creating
            purchase.setPrice(movie.getSalePrice());
        }

        log.info("This User: {}-{} purchased this movie: {}-{} for: {}", user.getId(),
                user.getName(), movie.getId(), movie.getTitle(), purchase.getPrice());

        purchase.setUser(user);
        purchase.setMovie(movie);

        getPurchaseRepository().save(purchase);

        return purchase;
    }
}
