package com.example.kh.controller;


import com.example.kh.model.FavoriteMovie;
import com.example.kh.repository.FavoriteMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin
@RequiredArgsConstructor
public class FavoriteMovieController {

    private final FavoriteMovieRepository repo;



    @GetMapping
    public List<FavoriteMovie> all() {
        return repo.findAll();
    }

    @PostMapping
    public FavoriteMovie save(@RequestBody FavoriteMovie movie) {
        return repo.save(movie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}