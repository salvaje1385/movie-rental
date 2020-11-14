package com.movie.rental.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.movie.rental.model.Movie;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;
import com.movie.rental.repository.MovieRepository;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.repository.RentalRepository;
import com.movie.rental.repository.UserRepository;
import com.movie.rental.service.dto.RentalDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing rentals.
 */
@Service
@Slf4j // lombok
public class RentalService extends AbstractService {

    /**
     * Full constructor
     * @param userRepository An {@link UserRepository}
     * @param movieRepository A {@link MovieRepository}
     * @param purchaseRepository A {@link purchaseRepository}
     * @param rentalRepository A {@link rentalRepository}
     */
    public RentalService(final UserRepository userRepository,
            final MovieRepository movieRepository,
            final PurchaseRepository purchaseRepository,
            final RentalRepository rentalRepository) {
        super(userRepository, movieRepository, purchaseRepository,
                rentalRepository);
    }

    /**
     * Save or update a Rental
     * @param rentalDTO The RentalDTO
     * @return The Rental created
     */
    public Rental saveOrUpdateRental(final RentalDTO rentalDTO) {
        log.info("saveOrUpdateRental: {}", rentalDTO);

        final User user = checkIfUserExists(rentalDTO.getUserId());
        final Movie movie = checkIfMovieExists(rentalDTO.getMovieId());

        final Date dueDate = new Date(rentalDTO.getDueDate());
        // If the User is returning the movie this value will be not null
        final Date returnDate = rentalDTO.getReturnDate() != null
                ? new Date(rentalDTO.getReturnDate()) : null;


        final Rental rental;
        if (rentalDTO.getId() != null) {
            // Updating
            rental = checkIfRentalExists(rentalDTO.getId());
            // If updating we take the old price
            rental.setPrice(rental.getPrice());
        } else {
            // Creating
            rental = new Rental();
            // The Price is only assigned when creating
            rental.setPrice(movie.getRentalPrice());
        }

        log.info("This User: {}-{} rented this movie: {}-{} for: {}, having as Due Date: {}, "
                + "Return Date: {}, Penalty: {}", user.getId(), user.getName(),
                movie.getId(), movie.getTitle(), rental.getPrice(), dueDate,
                returnDate, rentalDTO.getPenalty());

        rental.setUser(user);
        rental.setMovie(movie);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        rental.setPenalty(rentalDTO.getPenalty());

        getRentalRepository().save(rental);

        return rental;
    }

}
