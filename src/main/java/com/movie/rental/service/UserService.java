package com.movie.rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.rental.model.Movie;
import com.movie.rental.model.User;
import com.movie.rental.service.dto.LikeDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing users.
 */
@Service
@Slf4j // lombok
@Getter
@Setter
public class UserService extends AbstractService {

    @Autowired
    private MovieService movieService;

    /**
     * Use this method when a User likes/unlikes a Movie
     * @param likeDTO The LikeDTO
     * @return The User who liked the Movie
     */
    public User likeMovie(final LikeDTO likeDTO) {
        log.info("Like/unlike a Movie: {}", likeDTO);

        final User userObj = checkIfUserExists(likeDTO.getUserId());
        final Movie movieObj = checkIfMovieExists(likeDTO.getMovieId());

        log.info("This User: {}-{} {} this movie: {}-{}", userObj.getId(),
                userObj.getUsername(), likeDTO.getLike() ? "likes" : "unlikes",
                movieObj.getId(), movieObj.getTitle());

        if (likeDTO.getLike()) {
            if (!movieObj.getUsersWhoLike().contains(userObj)) {
                // The User is liking the Movie
                movieObj.getUsersWhoLike().add(userObj);

                getMovieRepository().save(movieObj);

                // Increase the Movie's likes count
                getMovieService().increaseLikes(movieObj);
            } else {
                log.info("Not saving the movie like because already existed");
            }
        } else {
            if (movieObj.getUsersWhoLike().contains(userObj)) {
                // The User is unliking the Movie
                movieObj.getUsersWhoLike().remove(userObj);

                getMovieRepository().save(movieObj);

                // Decrease the Movie's likes count
                getMovieService().decreaseLikes(movieObj);
            } else {
                log.info("Not removing the movie like because didn't existed");
            }
        }

        return userObj;
    }

}
