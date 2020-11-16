package com.movie.rental.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Used for the Web Pages RequestMappings
 */
@Controller
public class AppController {

    /**
     * Request mapping for the Movies page
     * @return The Movies page path
     */
    @RequestMapping("/movierental")
    public String movieRentalMapping(){
        return "movierental";
    }

}