package com.example.Mutantes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {
    private MutantDetector mutantDetector;
    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }
    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontales")
    void testMutantHorizontal() {
        String[] dna = {
                "AAAAAA",
                "CCCCCC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    @DisplayName("Debe detectar mutante con secuencias verticales")
    void testMutantVertical() {
        String[] dna = {
                "ATGCGA",
                "ATGCGA",
                "ATGCGA",
                "ATGCGA",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    @DisplayName("Debe detectar mutante con diagonales")
    void testMutantDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    @DisplayName("No debe detectar humano (sin secuencias)")
    void testHumanNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    @DisplayName("No debe detectar humano con solo UNA secuencia")
    void testHumanOneSequence() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    @DisplayName("Debe manejar matriz vacía o nula retornando false")
    void testInvalidInputs() {
        assertFalse(mutantDetector.isMutant(null));
        assertFalse(mutantDetector.isMutant(new String[]{}));
    }
    @Test
    @DisplayName("Debe retornar false si la matriz no es cuadrada (NxN)")
    void testNonSquareMatrix() {
        String[] dna = {
                "ABC",
                "DEF"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    @DisplayName("Debe retornar false si contiene caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
                "ATGX",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }
}