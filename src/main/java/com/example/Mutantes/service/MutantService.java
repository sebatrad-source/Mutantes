package com.example.Mutantes.service;

import com.example.Mutantes.entity.DnaRecord;
import com.example.Mutantes.exception.DnaHashCalculationException;
import com.example.Mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {
    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    public boolean analyzeDna(String[] dna) {
        String dnaHash = calculateHash(dna);
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }
        boolean isMutant = mutantDetector.isMutant(dna);
        DnaRecord record = DnaRecord.builder()
                .dnaHash(dnaHash)
                .isMutant(isMutant)
                .build();
        dnaRecordRepository.save(record);
        return isMutant;
    }
    private String calculateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String joinedDna = String.join("", dna);
            byte[] encodedhash = digest.digest(joinedDna.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException("Error calculando hash SHA-256 para el ADN", e);
        }
    }
}
