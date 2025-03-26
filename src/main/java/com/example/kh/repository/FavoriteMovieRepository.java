package com.example.kh.repository;

import com.example.kh.model.FavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

}
