package com.example.sudokuapp;

import com.example.sudokuapp.entity.Puzzle;
import com.example.sudokuapp.repository.PuzzleRepository;
import com.example.sudokuapp.util.SudokuGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
public class SudokuappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SudokuappApplication.class, args);
	}

	@Bean
	public CommandLineRunner initPuzzle(PuzzleRepository puzzleRepository) {
		return args -> {
			if (puzzleRepository.count() == 0) {
				System.out.println("🧩 パズルを自動生成中...");

				Map<String, Integer> difficultyMap = Map.of(
					"EASY", 30,
					"NORMAL", 40,
					"HARD", 50
				);

				for (Map.Entry<String, Integer> entry : difficultyMap.entrySet()) {
					String difficulty = entry.getKey();
					int blanks = entry.getValue();

					for (int i = 0; i < 5; i++) {
						String[] pair = SudokuGenerator.generatePuzzle(blanks);
						Puzzle puzzle = new Puzzle();
						puzzle.setDifficulty(difficulty);
						puzzle.setQuestion(pair[0]);
						puzzle.setAnswer(pair[1]);
						puzzleRepository.save(puzzle);
						System.out.println("✅ " + difficulty + " 問題 " + (i + 1) + " を登録しました");
					}
				}

				System.out.println("🎉 ナンプレ問題の初期データ登録完了");
			}
		};
	}

}
