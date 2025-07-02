package com.example.sudokuapp.entity;

import jakarta.persistence.*;

@Entity
public class Puzzle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String difficulty; //EASY / NORMAL / HARD
    private String question; // 81文字の文字列
    private String answer; //正解も文字列で保持

    //Getter
    public Long getId() {
        return id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    //Setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}