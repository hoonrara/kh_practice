package com.example.kh.controller;

import com.example.kh.service.TmdbService;
import com.example.kh.service.WeatherService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class WeatherController {


    private final WeatherService weatherService;
    private final TmdbService tmdbService;

    public WeatherController(WeatherService weatherService, TmdbService tmdbService) {
        this.weatherService = weatherService;
        this.tmdbService = tmdbService;
    }

    @GetMapping("/recommend")
    public Object recommend(@RequestParam String city) {
        String mood = weatherService.getMoodByCity(city);
        return tmdbService.getMoviesByGenre(mood);
    }
}