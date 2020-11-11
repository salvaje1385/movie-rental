package com.movie.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.rental.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
