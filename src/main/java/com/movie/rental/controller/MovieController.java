package com.movie.rental.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.rental.exception.ResourceNotFoundException;
import com.movie.rental.model.Movie;
import com.movie.rental.repository.MovieRepository;

@RestController
public class MovieController {

    private final MovieRepository movieRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public Page<Movie> getMovies(final Pageable pageable) {
        LOGGER.info("Get Movies: {}", pageable);
        return movieRepository.findByOrderByTitleAsc(pageable);
    }

    @PostMapping("/movies")
    public Movie createMovie(@Valid @RequestBody final Movie movie) {
        LOGGER.info("Create Movie: {}", movie);
        return movieRepository.save(movie);
    }

    @PutMapping("movies/{id}")
    public Movie updateMovie(@PathVariable final Long id,
                                   @Valid @RequestBody final Movie movieRequest) {
        LOGGER.info("Update Movie: id: {}, movie: {}", id, movieRequest);
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(movieRequest.getTitle());
                    movie.setDescription(movieRequest.getDescription());
                    movie.setStock(movieRequest.getStock());
                    movie.setRentalPrice(movieRequest.getRentalPrice());
                    movie.setSalePrice(movieRequest.getSalePrice());
                    movie.setAvailable(movieRequest.getAvailable());
                    return movieRepository.save(movie);
                }).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + id));
    }

    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable final Long movieId) {
        LOGGER.info("Delete Movie: {}", movieId);
        return movieRepository.findById(movieId)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + movieId));
    }
}
