package com.example.Mutantes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta con estadísticas de las verificaciones de ADN")
public class StatsResponse {

    @Schema(description = "Cantidad total de ADNs mutantes detectados", example = "40")
    @JsonProperty("count_mutant_dna")
    private long countMutantDna;

    @Schema(description = "Cantidad total de ADNs humanos detectados", example = "100")
    @JsonProperty("count_human_dna")
    private long countHumanDna;

    @Schema(description = "Proporción de mutantes respecto al total (mutantes / humanos)", example = "0.4")
    private double ratio;
}