package com.example.kh.controller;

import com.example.kh.service.TmdbService;
import com.example.kh.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class WeatherController {


    private final WeatherService weatherService;
    private final TmdbService tmdbService;



    @GetMapping("/recommend")
    public Object recommend(@RequestParam String city) {
        String mood = weatherService.getMoodByCity(city);
        return tmdbService.getMoviesByGenre(mood);
    }
}