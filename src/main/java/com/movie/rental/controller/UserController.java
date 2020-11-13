package com.movie.rental.controller;

import java.net.URISyntaxException;

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
import com.movie.rental.model.User;
import com.movie.rental.repository.UserRepository;
import com.movie.rental.service.UserService;
import com.movie.rental.service.dto.LikeDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j // lombok
@RestController
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserController(final UserRepository userRepository,
            final UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Get all the users
     * @param pageable A {@link Pageable} object
     * @return A {@link Page} of {@link User}
     */
    @GetMapping("/users")
    public Page<User> getUsers(final Pageable pageable) {
        log.info("Get Users: {}", pageable);
        return userRepository.findAll(pageable);
    }

    /**
     * Create an user
     * @param user The {@link User} object
     * @return The created {@link User}
     */
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody final User user) {
        log.info("Create User: {}", user);
        return userRepository.save(user);
    }

    /**
     * Update an {@link User} object
     * @param id The {@link User}'s Id
     * @param userRequest The {@link User} to update
     * @return The updated {@link User}
     */
    @PutMapping("users/{id}")
    public User updateUser(@PathVariable final Long id,
                                   @Valid @RequestBody final User userRequest) {
        log.info("Update User: id: {}, user: {}", id, userRequest);
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userRequest.getName());
                    user.setEmail(userRequest.getEmail());
                    user.setPassword(userRequest.getPassword());
                    user.setPhone(userRequest.getPhone());
                    user.setAddress(userRequest.getAddress());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    /**
     * Delete an {@link User}
     * @param userId The {@link User}'s Id
     * @return A {@link ResponseEntity} object
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long userId) {
        log.info("Delete User: {}", userId);
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    /**
     * Used when Users like a Movie
     * @param likeDTO The {@link LikeDTO}
     * @return A {@link ResponseEntity} object
     * @throws URISyntaxException
     */
    @PostMapping("/users/likeMovie")
    public ResponseEntity<?> createUser(@Valid @RequestBody final LikeDTO likeDTO)
            throws URISyntaxException {

        log.info("REST request to like a movie: {}", likeDTO);

        getUserService().likeMovie(likeDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Getter for the {@link UserService}
     * @return The {@link UserService}
     */
    public UserService getUserService() {
        return this.userService;
    }

}
