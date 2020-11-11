package com.movie.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.rental.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
