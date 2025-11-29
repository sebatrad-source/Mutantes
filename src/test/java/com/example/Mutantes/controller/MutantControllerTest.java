package com.example.Mutantes.controller;

import com.example.Mutantes.dto.StatsResponse;
import com.example.Mutantes.service.MutantService;
import com.example.Mutantes.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    @DisplayName("POST /mutant retorna 200 OK si es mutante")
    void testMutantDetected() throws Exception {
        when(mutantService.analyzeDna(any())).thenReturn(true);
        String json = "{\"dna\":[\"AAAA\", \"CCCC\", \"TCAG\", \"GGTC\"]}";
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("POST /mutant retorna 403 Forbidden si es humano")
    void testHumanDetected() throws Exception {
        when(mutantService.analyzeDna(any())).thenReturn(false);
        String json = "{\"dna\":[\"AAAT\", \"CCCG\", \"TCAG\", \"GGTC\"]}";
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }
    @Test
    @DisplayName("GET /stats retorna estadísticas correctamente")
    void testGetStats() throws Exception {
        when(statsService.getStats()).thenReturn(new StatsResponse(10, 20, 0.5));
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(10))
                .andExpect(jsonPath("$.count_human_dna").value(20))
                .andExpect(jsonPath("$.ratio").value(0.5));
    }
}