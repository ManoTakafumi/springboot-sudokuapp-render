package com.example.sudokuapp.controller;

import com.example.sudokuapp.entity.Puzzle;
import com.example.sudokuapp.repository.PuzzleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.sudokuapp.entity.SolveRecord;
import com.example.sudokuapp.entity.User;
import com.example.sudokuapp.repository.SolveRecordRepository;
import com.example.sudokuapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/puzzles")
public class PuzzleController {

    @Autowired
    private PuzzleRepository puzzleRepository;

    @Autowired
    private SolveRecordRepository recordRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/select")
    public String showDifficulty() {
        return "difficulty";
    }

    @GetMapping("/play")
    public String playPuzzle(@RequestParam String difficulty, Model model) {
        List<Puzzle> puzzles = puzzleRepository.findByDifficulty(difficulty);
        Collections.shuffle(puzzles);

        if (puzzles.isEmpty()) {
            return "redirect:/puzzles/select?error=nodata";
        }

        Puzzle puzzle = puzzles.get(0);
        // model.addAttribute("puzzle", puzzle);
        // model.addAttribute("startTime", System.currentTimeMillis());
        int[][] grid = convertToGrid(puzzle.getQuestion());

        model.addAttribute("puzzle", puzzle);
        model.addAttribute("grid", grid);
        model.addAttribute("startTime", System.currentTimeMillis());
        return "puzzle";
    }

    //81文字→9x9の2次元配列へ変換
    private int[][] convertToGrid(String question) {
        int[][] grid = new int[9][9];
        for (int i = 0; i < 81; i++) {
            char c = question.charAt(i);
            grid[i / 9][i % 9] = Character.getNumericValue(c);
        }
        return grid;
    }

    @PostMapping("/solve")
    public String checkAnswer(@RequestParam Long puzzleId, @RequestParam String answer, 
                              @RequestParam long startTime, Model model) {
        Puzzle puzzle = puzzleRepository.findById(puzzleId).orElseThrow();
        boolean correct = puzzle.getAnswer().equals(answer);
        int time = (int)((System.currentTimeMillis() - startTime) / 1000);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        SolveRecord record = new SolveRecord();
        record.setPuzzle(puzzle);
        record.setUser(user);
        record.setTime(time);
        record.setCorrect(correct);
        record.setSolvedAt(LocalDateTime.now());
        recordRepository.save(record);

        model.addAttribute("correct", correct);
        model.addAttribute("time", time);
        return "result";
    }

    @GetMapping("/mypage")
    public String myPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        List<SolveRecord> records = recordRepository.findByUser(user);
        model.addAttribute("records", records);
        return "mypage";
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        List<SolveRecord> all = recordRepository.findAll();

        //正解のみ
        List<SolveRecord> correctRecords = all.stream()
            .filter(SolveRecord::isCorrect)
            .sorted(Comparator.comparingInt(SolveRecord::getTime))
            .toList();
        // all.sort(Comparator.comparingInt(SolveRecord::getTime));
        model.addAttribute("records", correctRecords);
        return "ranking";
    }
}