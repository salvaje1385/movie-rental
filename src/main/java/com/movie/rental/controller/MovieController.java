package com.movie.rental.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.rental.model.Movie;
import com.movie.rental.repository.MovieRepository;

@Controller
@RequestMapping("/movies/")
public class MovieController {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("list")
    public String listMovies(final Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "index";
    }

    @GetMapping("createmovie")
    public String createMovie(final Movie movie) {
        return "add-movie";
    }

    @PostMapping("addmovie")
    public String addMovie(@Valid final Movie movie, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "add-movie";
        }

        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String editForm(@PathVariable("id") final Long id, final Model model) {
        final Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
        model.addAttribute("movie", movie);
        return "update-movie";
    }

//    @GetMapping("/movies")
//    public String getIndex(final Model model) {
//        model.addAttribute("movies", movieRepository.findAll());
//        return "redirect:/index";
//    }

//    @GetMapping("/movies")
//    public Page<Movie> getMovies(final Pageable pageable) {
//        return movieRepository.findAll(pageable);
//    }

//
//    @PostMapping("/movies")
//    public Movie createMovie(@Valid @RequestBody final Movie movie) {
//        return movieRepository.save(movie);
//    }

    @PostMapping("update/{id}")
    public String updateMovie(@PathVariable("id") final long id, @Valid final Movie movie, final BindingResult result,
            final Model model) {
        if (result.hasErrors()) {
            movie.setId(id);
            return "update-movie";
        }

        movieRepository.save(movie);
        model.addAttribute("movie", movieRepository.findAll());
        return "redirect:/movies/list";
    }

    @GetMapping("delete/{id}")
    public String deleteMovie(@PathVariable("id") final Long id, final Model model) {
        final Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
        movieRepository.delete(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "redirect:/movies/list";
    }

//    @PutMapping("update/{id}")
//    public Movie updateMovie(@PathVariable final Long id,
//                                   @Valid @RequestBody final Movie movieRequest) {
//        return movieRepository.findById(id)
//                .map(movie -> {
//                    movie.setTitle(movieRequest.getTitle());
//                    movie.setDescription(movieRequest.getDescription());
//                    movie.setStock(movieRequest.getStock());
//                    movie.setRentalPrice(movieRequest.getRentalPrice());
//                    movie.setSalePrice(movieRequest.getSalePrice());
//                    movie.setAvailable(movieRequest.getAvailable());
//                    return movieRepository.save(movie);
//                }).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + id));
//    }


//    @DeleteMapping("/movies/{movieId}")
//    public ResponseEntity<?> deleteMovie(@PathVariable final Long movieId) {
//        return movieRepository.findById(movieId)
//                .map(movie -> {
//                    movieRepository.delete(movie);
//                    return ResponseEntity.ok().build();
//                }).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + movieId));
//    }
}
