package com.example.Mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia para recibir la secuencia de ADN")
public class DnaRequest {
    @Schema(
            description = "Array de Strings que representan la secuencia de ADN (Matriz NxN)",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "La secuencia de ADN no puede ser nula")
    @NotEmpty(message = "La secuencia de ADN no puede estar vacía")
    private String[] dna;
}