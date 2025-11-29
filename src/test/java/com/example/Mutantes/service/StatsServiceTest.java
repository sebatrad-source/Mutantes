package com.example.Mutantes.service;

import com.example.Mutantes.dto.StatsResponse;
import com.example.Mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Debe calcular el ratio correctamente")
    void testGetStats() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();
        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio());
    }
    @Test
    @DisplayName("Debe manejar división por cero (0 humanos)")
    void testDivisionByZero() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();
        assertEquals(10.0, response.getRatio()); // Definimos que si countHuman=0, ratio=countMutant
    }
}