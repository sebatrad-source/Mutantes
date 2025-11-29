package com.example.Mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Estructura estandarizada para respuestas de error")
public class ErrorResponse {

    @Schema(description = "Marca de tiempo del error", example = "2025-11-29T10:15:30")
    private LocalDateTime timestamp;

    @Schema(description = "Código de estado HTTP", example = "400")
    private int status;

    @Schema(description = "Tipo de error", example = "Bad Request")
    private String error;

    @Schema(description = "Mensaje detallado del error", example = "La matriz de ADN debe ser cuadrada (NxN)")
    private String message;

    @Schema(description = "Ruta de la petición que generó el error", example = "/mutant")
    private String path;
}