package com.movie.rental.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @RequestMapping("/movierental")
    public String movieRentalMapping(){
        return "movierental";
    }

}