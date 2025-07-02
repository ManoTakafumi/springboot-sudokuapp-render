package com.example.sudokuapp.repository;

import com.example.sudokuapp.entity.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {
    List<Puzzle> findByDifficulty(String difficulty);
}