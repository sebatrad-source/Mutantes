package com.example.Mutantes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {
    private static final Pattern VALID_CHARACTERS_PATTERN = Pattern.compile("^[ATCG]+$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) {
            return false;
        }
        final int n = dna.length;
        for (String row : dna) {
            if (row == null) {
                return false;
            }
            if (row.length() != n) {
                return false;
            }
            if (!VALID_CHARACTERS_PATTERN.matcher(row).matches()) {
                return false;
            }
        }
        return true;
    }
}