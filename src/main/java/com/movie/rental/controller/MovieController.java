package com.movie.rental.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    private final static String MOVIE_PAGE_SIZE = "5";

    @Autowired
    public MovieController(final MovieRepository movieRepository,
            final MovieService movieService) {
        this.movieRepository = movieRepository;
        this.movieService = movieService;
    }

    /**
     * Get all the movies,
     * search by title,
     * filter by available/unavailable movies,
     * indicate the page number,
     * indicate the page size,
     * or sort all the Movie's property names
     * @param title Search Movies by title (not required)
     * @param available Filter Movies by available/unavailable (not required)
     * @param page Indicate the page size (not required, default=0)
     * @param size Indicate the page size (not required, default=MOVIE_PAGE_SIZE)
     * @param sort Sort all the Movie's property names
     * @return A Map containing the response properties
     */
    @GetMapping("/movies")
    public ResponseEntity<Map<String, Object>> getAllMoviesPage(
        @RequestParam(required = false) final String title,
        @RequestParam(required = false) final Boolean available,
        @RequestParam(defaultValue = "0") final int page,
        @RequestParam(defaultValue = MOVIE_PAGE_SIZE) final int size,
        @RequestParam(defaultValue = "title,asc") final String[] sort) {

      try {
        log.info("Get Movies parameters: title: {}, available: {}, page: {}, size: {}, sort: {}",
                title, available, page, size, sort);

        final List<Order> orders = new ArrayList<Order>();

        if (sort[0].contains(",")) {
          // will sort more than 2 fields
          // sortOrder="field, direction"
          for (final String sortOrder : sort) {
            final String[] _sort = sortOrder.split(",");
            orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
          }
        } else {
          // sort=[field, direction]
          orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        List<Movie> movies = new ArrayList<Movie>();
        final Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        Page<Movie> pageResult = null;
        if (title == null && available == null) {
            pageResult = movieRepository.findAll(pagingSort);
        } else if (title != null && available != null) {
            pageResult = movieRepository.findByTitleContainingIgnoreCaseAndAvailable(
                    title, available, pagingSort);
        } else if (title != null) {
            pageResult = movieRepository.findByTitleContainingIgnoreCase(title, pagingSort);
        } else if (available != null) {
            pageResult = movieRepository.findByAvailable(available, pagingSort);
        }

        if (pageResult == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        movies = pageResult.getContent();

        if (movies.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Returning {} movies", movies.size());

        final Map<String, Object> response = new HashMap<>();
        response.put("movies", movies);
        response.put("currentPage", pageResult.getNumber());
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
      } catch (final Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    /**
     * Get a sort direction object from a direction String
     * @param direction Ascending ("asc") or descending ("desc")
     * @return The Sort.Direction object
     */
    private Sort.Direction getSortDirection(final String direction) {
        if (direction.equals("asc")) {
          return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
          return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
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
