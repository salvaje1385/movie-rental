package com.movie.rental.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.rental.model.Rental;
import com.movie.rental.model.User;
import com.movie.rental.repository.RentalRepository;
import com.movie.rental.service.RentalService;
import com.movie.rental.service.UserDetailsImpl;
import com.movie.rental.service.dto.RentalDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Rental Controller
 */
@Slf4j // lombok
@RestController
public class RentalController extends AbstractController {

    private final RentalRepository rentalRepository;

    private final RentalService rentalService;

    @Autowired
    public RentalController(final RentalRepository rentalRepository,
            final RentalService rentalService) {
        this.rentalRepository = rentalRepository;
        this.rentalService = rentalService;
    }

    /**
     * Get all the rentals
     * @param pageable A {@link Pageable} object
     * @return A {@link Page} of {@link Rental}
     */
    @GetMapping("/rentals")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Rental> getRentals(final Pageable pageable) {
        log.info("Get Rentals: {}", pageable);

        final UserDetailsImpl userDetails = getLoggedInUser();

        // Regular users only get the list of rentals they've made
        if (!userDetails.isAdmin()) {
            final User user = getRentalService().checkIfUserExists(userDetails.getId());
            log.info("Getting this User rentals: {} - {}", user.getId(), user.getUsername());

            return rentalRepository.findByUser(user);
        } else {
            return rentalRepository.findAll(pageable).getContent();
        }
    }

    /**
     * Delete a {@link Rental}
     * @param rentalId The {@link Rental}'s Id
     * @return A {@link ResponseEntity} object
     */
    @DeleteMapping("/rentals/{rentalId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRental(@PathVariable final Long rentalId) {
        log.info("Delete Rental: {}", rentalId);

        getRentalService().deleteRental(rentalId);

        return ResponseEntity.ok().build();
    }

    /**
     * Create a rental
     * @param rental The {@link RentalDTO} object
     * @return The created {@link Rental}
     */
    @PostMapping("/rentals")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Rental createRental(@Valid @RequestBody final RentalDTO rentalDTO) {
        log.info("Create a Rental: {}", rentalDTO);
        return getRentalService().saveOrUpdateRental(rentalDTO);
    }

    /**
     * Update a rental
     * @param id The {@link Rental}'s Id
     * @param rental The {@link RentalDTO} object
     * @return The updated {@link Rental}
     */
    @PutMapping("rentals/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Rental updateRental(@PathVariable final Long id,
            @Valid @RequestBody final RentalDTO rentalDTO) {

        rentalDTO.setId(id);
        log.info("Update a Rental: {}", rentalDTO);
        return getRentalService().saveOrUpdateRental(rentalDTO);
    }

    /**
     * Getter for the {@link RentalService}
     * @return The {@link RentalService}
     */
    public RentalService getRentalService() {
        return this.rentalService;
    }
}
