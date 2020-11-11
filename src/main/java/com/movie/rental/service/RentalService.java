package com.movie.rental.service;

import org.springframework.stereotype.Service;

import com.movie.rental.repository.RentalRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing rentals.
 */
@Service
@Slf4j // lombok
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(final RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

}
