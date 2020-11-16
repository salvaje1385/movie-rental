package com.movie.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.movie.rental.exception.ResourceNotFoundException;
import com.movie.rental.model.MovieUpdates;
import com.movie.rental.repository.MovieUpdatesRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Movie Updates Controller
 */
@Slf4j // lombok
@RestController
public class MovieUpdatesController {

    private final MovieUpdatesRepository movieUpdatesRepository;

    @Autowired
    public MovieUpdatesController(final MovieUpdatesRepository movieUpdatesRepository) {
        this.movieUpdatesRepository = movieUpdatesRepository;
    }

    /**
     * Get all the movie updates
     * @param pageable A {@link Pageable} object
     * @return A {@link Page} of {@link MovieUpdates}
     */
    @GetMapping("/movieUpdates")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<MovieUpdates> getMovieUpdates(final Pageable pageable) {
        log.info("Get MovieUpdates: {}", pageable);
        return movieUpdatesRepository.findAll(pageable);
    }

    /**
     * Delete a {@link MovieUpdates}
     * @param movieUpdatesId The {@link MovieUpdates}'s Id
     * @return A {@link ResponseEntity} object
     */
    //@DeleteMapping("/movieUpdates/{movieUpdatesId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMovieUpdates(@PathVariable final Long movieUpdatesId) {
        log.info("Delete MovieUpdates: {}", movieUpdatesId);
        return movieUpdatesRepository.findById(movieUpdatesId)
                .map(movieUpdates -> {
                    movieUpdatesRepository.delete(movieUpdates);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("MovieUpdates not found with id " + movieUpdatesId));
    }
}
