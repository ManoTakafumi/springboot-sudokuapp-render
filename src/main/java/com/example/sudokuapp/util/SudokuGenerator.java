package com.example.sudokuapp.util;

import org.springframework.stereotype.Component;

import java.util.*;

// @Component
public class SudokuGenerator {

    private static final int SIZE = 9;
    private static final int SUBGRID = 3;
    private static final Random random = new Random();

    //問題と解答を生成して返す（長さ81の文字列)
    public static String[] generatePuzzle(int blanks) {
        int[][] solution = new int[SIZE][SIZE];
        fillBoard(solution);

        int[][] puzzle = deepCopy(solution);
        removeCells(puzzle, blanks);

        return new String[] {
            boardToString(puzzle),
            boardToString(solution)
        };
    }

    //再帰で盤面を埋める
    private static boolean fillBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    List<Integer> nums = getShuffledNumbers();
                    for (int num : nums) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (fillBoard(board)) return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Integer> getShuffledNumbers() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) nums.add(i);
        Collections.shuffle(nums);
        return nums;
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }

        int startRow = row / SUBGRID * SUBGRID;
        int startCol = col / SUBGRID * SUBGRID;
        for (int i = 0; i < SUBGRID; i++) {
            for (int j = 0; j < SUBGRID; j++) {
                if (board[startRow + i][startCol + j] == num) return false;
            }
        }
        return true;
    }

    private static void removeCells(int[][] board, int blanks) {
        int count = 0;
        while (count < blanks) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                count++;
            }
        }
    }

    private static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) 
            System.arraycopy(original[i], 0, copy[i], 0, SIZE);
        return copy;
    }

    private static String boardToString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }
}