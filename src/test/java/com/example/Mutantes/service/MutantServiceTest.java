package com.example.Mutantes.service;

import com.example.Mutantes.entity.DnaRecord;
import com.example.Mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    @Test
    @DisplayName("Si el ADN ya existe en BD, debe retornar el valor cacheado")
    void testAnalyzeExistingDna() {
        DnaRecord existingRecord = new DnaRecord();
        existingRecord.setMutant(true);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));
        boolean result = mutantService.analyzeDna(new String[]{"AAAA"});
        assertTrue(result);
        verify(mutantDetector, never()).isMutant(any());
    }
    @Test
    @DisplayName("Si el ADN es nuevo, debe usar el detector y guardar en BD")
    void testAnalyzeNewDna() {
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(any())).thenReturn(true);
        boolean result = mutantService.analyzeDna(new String[]{"AAAA"});
        assertTrue(result);
        verify(mutantDetector, times(1)).isMutant(any());
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }
}