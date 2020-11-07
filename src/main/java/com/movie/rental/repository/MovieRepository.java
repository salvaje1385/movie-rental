package com.movie.rental.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.rental.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByTitle(String title);
}
