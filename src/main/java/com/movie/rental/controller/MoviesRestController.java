package com.movie.rental.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/action")
public class MoviesRestController {

    @GetMapping("/loggedout")
    public String logout() {
        return "You're logout";
    }

}
