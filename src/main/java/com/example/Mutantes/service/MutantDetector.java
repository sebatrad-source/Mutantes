package com.example.Mutantes.service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4;
    private static final Pattern VALID_DNA_PATTERN = Pattern.compile("^[ATCG]+$");

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) return false;
        final int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n || !VALID_DNA_PATTERN.matcher(row).matches()) {
                return false;
            }
        }
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        int sequenceCount = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row][col+1] &&
                base == matrix[row][col+2] &&
                base == matrix[row][col+3];
    }
    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col] &&
                base == matrix[row+2][col] &&
                base == matrix[row+3][col];
    }
    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col+1] &&
                base == matrix[row+2][col+2] &&
                base == matrix[row+3][col+3];
    }
    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row-1][col+1] &&
                base == matrix[row-2][col+2] &&
                base == matrix[row-3][col+3];
    }
}