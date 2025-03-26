package com.example.kh.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String tmdbKey;

    public Object getMoviesByGenre(String mood) {
        String genreId = switch (mood) {
            case "행복" -> "35";   // Comedy
            case "우울" -> "18";   // Drama
            case "지루함" -> "28"; // Action
            default -> "10749";    // Romance
        };

        String url = "https://api.themoviedb.org/3/discover/movie?with_genres=" + genreId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tmdbKey);
        headers.set("accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        return response.getBody();
    }
}
