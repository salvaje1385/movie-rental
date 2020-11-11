package com.movie.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.rental.model.MovieUpdates;

@Repository
public interface MovieUpdatesRepository extends JpaRepository<MovieUpdates, Long> {

}

