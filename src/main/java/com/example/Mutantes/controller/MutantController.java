package com.example.Mutantes.controller;

import com.example.Mutantes.dto.DnaRequest;
import com.example.Mutantes.dto.StatsResponse;
import com.example.Mutantes.service.MutantService;
import com.example.Mutantes.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para detección de mutantes mediante análisis de ADN")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @Operation(summary = "Verificar si un ADN corresponde a un mutante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El ADN analizado es de un MUTANTE"),
            @ApiResponse(responseCode = "403", description = "El ADN analizado es de un HUMANO"),
            @ApiResponse(responseCode = "400", description = "La secuencia de ADN es inválida (formato incorrecto, caracteres inválidos, etc.)")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());

        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Obtener estadísticas de las verificaciones")
    @ApiResponse(responseCode = "200", description = "Estadísticas retornadas correctamente")
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}