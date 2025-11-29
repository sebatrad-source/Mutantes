package com.example.Mutantes.service;

import com.example.Mutantes.dto.StatsResponse;
import com.example.Mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository dnaRecordRepository;
    public StatsResponse getStats() {
        long countMutantDna = dnaRecordRepository.countByIsMutant(true);
        long countHumanDna = dnaRecordRepository.countByIsMutant(false);

        double ratio = 0.0;
        if (countHumanDna > 0) {
            ratio = (double) countMutantDna / countHumanDna;
        } else if (countMutantDna > 0) {
            ratio = countMutantDna;
        }
        return new StatsResponse(countMutantDna, countHumanDna, ratio);
    }
}