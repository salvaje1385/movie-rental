package com.movie.rental.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.rental.model.Movie;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;
import com.movie.rental.service.dto.RentalDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing rentals.
 */
@Service
@Slf4j // lombok
@Getter
@Setter
public class RentalService extends AbstractService {

    @Autowired
    private MovieService movieService;

    /**
     * Save or update a Rental
     * @param rentalDTO The RentalDTO
     * @return The Rental created
     */
    public Rental saveOrUpdateRental(final RentalDTO rentalDTO) {
        log.info("saveOrUpdateRental: {}", rentalDTO);

        final User user = checkIfUserExists(rentalDTO.getUserId());
        final Movie movie = checkIfMovieExists(rentalDTO.getMovieId());
        Movie oldMovie = null;
        boolean creatingRental = false;

        final Date dueDate = new Date(rentalDTO.getDueDate());
        // If the User is returning the movie this value will be not null
        final Date returnDate = rentalDTO.getReturnDate() != null
                ? new Date(rentalDTO.getReturnDate()) : null;

        final Rental rental;
        if (rentalDTO.getId() != null) {
            // Updating
            rental = checkIfRentalExists(rentalDTO.getId());

            oldMovie = rental.getMovie();

            // If updating we take the old price
            rental.setPrice(rental.getPrice());
        } else {
            // Creating
            rental = new Rental();
            // The Price is only assigned when creating
            rental.setPrice(movie.getRentalPrice());

            creatingRental = true;
        }

        // Update the Movie stock
        updateMovieStock(creatingRental, movie, oldMovie, rental, rentalDTO, returnDate);

        log.info("This User: {}-{} rented this movie: {}-{} for: {}, having as Due Date: {}, "
                + "Return Date: {}, Penalty: {}", user.getId(), user.getUsername(),
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

    /**
     * Update the Movie stock
     * @param creatingRental True if we're creating a Rental, false if we're updating it
     * @param movie The Movie to set
     * @param oldMovie The previous Movie
     * @param rental The Rental object
     * @param rentalDTO The RentalDTO
     * @param returnDate If the User is returning the movie this value will be not null
     */
    private void updateMovieStock(final boolean creatingRental,
            final Movie movie, final Movie oldMovie, final Rental rental,
            final RentalDTO rentalDTO, final Date returnDate) {
        if (creatingRental) {
            // Decrease the Movie stock by one
            getMovieService().decreaseStock(movie);

        // If the Movie changes then update every Movie stock
        } else if (!movie.getId().equals(oldMovie.getId())) {
            getMovieService().increaseStock(oldMovie);
            getMovieService().decreaseStock(movie);

        } else if (movie.getId().equals(oldMovie.getId())) {

            // We're returning the Movie
            if (rental.getReturnDate() == null && returnDate != null) {
                getMovieService().increaseStock(oldMovie);

            // We're reverting the Movie return
            } else if (rental.getReturnDate() != null && returnDate == null) {
                getMovieService().decreaseStock(movie);
            }
        }
    }

    /**
     * Delete a Rental
     * @param rentalId The Rental Id
     */
    public void deleteRental(final Long rentalId) {
        log.info("Delete Rental: {}", rentalId);
        final Rental rental = checkIfRentalExists(rentalId);

        // On deletion we only increase the Movie stock if the Return Date is null,
        // if it's not null, that means that the Movie was already returned,
        // and the stock was already increased
        if (rental.getReturnDate() == null) {
            // Increase the Movie stock by one
            getMovieService().increaseStock(rental.getMovie());
        }

        getRentalRepository().delete(rental);
    }

}
