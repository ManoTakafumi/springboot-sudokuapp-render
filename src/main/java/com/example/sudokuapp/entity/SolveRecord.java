package com.example.sudokuapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SolveRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "puzzle_id")
    private Puzzle puzzle;

    private int time; //回答時間

    private boolean correct;

    @Column(name = "solve_at")
    private LocalDateTime solvedAt;

    // Getter
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public int getTime() {
        return time;
    }

    public boolean isCorrect() {
        return correct;
    }

    public LocalDateTime getSolvedAt() {
        return solvedAt;
    }

    //Setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setSolvedAt(LocalDateTime solvedAt) {
        this.solvedAt = solvedAt;
    }
}