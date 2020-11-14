package com.movie.rental.controller;

import javax.validation.Valid;

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
import com.movie.rental.service.MovieService;

import lombok.extern.slf4j.Slf4j;

@Slf4j // lombok
@RestController
public class MovieController {

    private final MovieRepository movieRepository;

    private final MovieService movieService;

    @Autowired
    public MovieController(final MovieRepository movieRepository,
            final MovieService movieService) {
        this.movieRepository = movieRepository;
        this.movieService = movieService;
    }

    /**
     * Get all the movies
     * @param pageable A {@link Pageable} object
     * @return A {@link Page} of {@link Movie}
     */
    @GetMapping("/movies")
    public Page<Movie> getMovies(final Pageable pageable) {
        log.info("Get Movies");
        return movieRepository.findByOrderByTitleAsc(pageable);
    }

    /**
     * Create a movie
     * @param movie The {@link Movie} object
     * @return The created {@link Movie}
     */
    @PostMapping("/movies")
    public Movie createMovie(@Valid @RequestBody final Movie movie) {
        log.info("Create Movie: {}", movie.getTitle());
        return movieRepository.save(movie);
    }

    /**
     * Update a {@link Movie} object
     * @param id The {@link Movie}'s Id
     * @param movieRequest The {@link Movie} to update
     * @return The updated {@link Movie}
     */
    @PutMapping("movies/{id}")
    public Movie updateMovie(@PathVariable final Long id,
                                   @Valid @RequestBody final Movie movieRequest) {
        log.info("Update Movie: id: {}, movie: {}", id, movieRequest.getTitle());
        return getMovieService().updateMovie(id, movieRequest);
    }

    /**
     * Delete a {@link Movie}
     * @param movieId The {@link Movie}'s Id
     * @return A {@link ResponseEntity} object
     */
    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable final Long movieId) {
        log.info("Delete Movie: {}", movieId);
        return movieRepository.findById(movieId)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + movieId));
    }

    /**
     * Getter for the {@link MovieService}
     * @return The {@link MovieService}
     */
    public MovieService getMovieService() {
        return this.movieService;
    }
}
