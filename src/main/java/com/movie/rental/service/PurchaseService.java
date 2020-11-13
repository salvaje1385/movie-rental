package com.movie.rental.service;

import org.springframework.stereotype.Service;

import com.movie.rental.model.Movie;
import com.movie.rental.model.Purchase;
import com.movie.rental.model.User;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.PurchaseRepository;
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
     */
    public PurchaseService(final UserRepository userRepository,
            final MovieRepository movieRepository,
            final PurchaseRepository purchaseRepository) {
        super(userRepository, movieRepository, purchaseRepository);
    }

    /**
     * Save a Purchase
     * @param purchaseDTO The PurchaseDTO
     * @return The Purchase created
     */
    public Purchase createPurchase(final PurchaseDTO purchaseDTO) {
        log.info("purchaseDTO: {}", purchaseDTO);

        final User user = checkIfUserExists(purchaseDTO.getUserId());
        final Movie movie = checkIfMovieExists(purchaseDTO.getMovieId());

        log.info("This User: {}-{} purchased this movie: {}-{} for: {}", user.getId(),
                user.getName(), movie.getId(), movie.getTitle(), purchaseDTO.getPrice());

        final Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setMovie(movie);
        purchase.setPrice(purchaseDTO.getPrice());

        getPurchaseRepository().save(purchase);

        return purchase;
    }
}
