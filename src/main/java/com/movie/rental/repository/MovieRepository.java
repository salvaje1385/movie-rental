package com.movie.rental.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.rental.model.Movie;

/**
 * Movie repository
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Movie> findByTitleContainingIgnoreCaseAndAvailable(String title,
            boolean available, Pageable pageable);

    Page<Movie> findByAvailable(boolean available, Pageable pageable);
}
