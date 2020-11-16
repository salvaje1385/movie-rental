package com.movie.rental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.rental.model.Purchase;
import com.movie.rental.model.User;

/**
 * Purchase repository
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUser(User user);
}
