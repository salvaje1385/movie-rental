package com.movie.rental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.rental.model.Rental;
import com.movie.rental.model.User;

/**
 * Rental repository
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUser(User user);

}
