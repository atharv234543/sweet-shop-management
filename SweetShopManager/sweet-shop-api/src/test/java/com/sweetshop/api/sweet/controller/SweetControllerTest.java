package com.sweetshop.api.sweet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.api.auth.service.JwtAuthFilter;
import com.sweetshop.api.auth.service.JwtTokenProvider;
import com.sweetshop.api.sweet.model.Sweet;
import com.sweetshop.api.sweet.service.SweetService;
import com.sweetshop.api.user.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

@WebMvcTest(SweetController.class)
@Import(SweetControllerTest.TestSecurityConfig.class)
class SweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SweetService sweetService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Sweet testSweet;

    @BeforeEach
    void setUp() {
        testSweet = new Sweet();
        testSweet.setId(1L);
        testSweet.setName("Chocolate Bar");
        testSweet.setCategory("Chocolate");
        testSweet.setPrice(2.50);
        testSweet.setQuantityInStock(10);
    }

    @Test
    @WithMockUser
    void getAllSweets_ReturnsSweetList() throws Exception {
        // Arrange
        List<Sweet> sweets = Arrays.asList(testSweet);
        when(sweetService.findAllSweets()).thenReturn(sweets);

        // Act & Assert
        mockMvc.perform(get("/api/sweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Chocolate Bar"))
                .andExpect(jsonPath("$[0].category").value("Chocolate"));

        verify(sweetService).findAllSweets();
    }

    @Test
    @WithMockUser
    void getSweetById_ExistingId_ReturnsSweet() throws Exception {
        // Arrange
        when(sweetService.findById(1L)).thenReturn(Optional.of(testSweet));

        // Act & Assert
        mockMvc.perform(get("/api/sweets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chocolate Bar"))
                .andExpect(jsonPath("$.price").value(2.50));
    }

    @Test
    @WithMockUser
    void getSweetById_NonExistingId_ReturnsNotFound() throws Exception {
        // Arrange
        when(sweetService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/sweets/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addSweet_ValidData_ReturnsCreatedSweet() throws Exception {
        // Arrange
        when(sweetService.saveSweet(any(Sweet.class))).thenReturn(testSweet);

        // Act & Assert
        mockMvc.perform(post("/api/sweets")
                .content(objectMapper.writeValueAsString(testSweet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chocolate Bar"));

        verify(sweetService).saveSweet(any(Sweet.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSweet_ValidData_ReturnsUpdatedSweet() throws Exception {
        // Arrange
        Sweet updateData = new Sweet();
        updateData.setName("Updated Chocolate Bar");
        updateData.setPrice(3.00);

        when(sweetService.updateSweet(eq(1L), any(Sweet.class))).thenReturn(testSweet);

        // Act & Assert
        mockMvc.perform(put("/api/sweets/1")
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
;

        verify(sweetService).updateSweet(eq(1L), any(Sweet.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSweet_NonExistingId_ReturnsNotFound() throws Exception {
        // Arrange
        Sweet updateData = new Sweet();
        updateData.setName("Updated Name");

        when(sweetService.updateSweet(eq(99L), any(Sweet.class)))
                .thenThrow(new RuntimeException("Sweet not found with ID: 99"));

        // Act & Assert
        mockMvc.perform(put("/api/sweets/99")
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteSweet_ExistingId_ReturnsNoContent() throws Exception {
        // Arrange
        doNothing().when(sweetService).deleteSweet(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/sweets/1")
)
                .andExpect(status().isNoContent());

        verify(sweetService).deleteSweet(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteSweet_NonExistingId_ReturnsNotFound() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Sweet not found with ID: 99"))
                .when(sweetService).deleteSweet(99L);

        // Act & Assert
        mockMvc.perform(delete("/api/sweets/99")
)
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser
    void purchaseSweet_WithQuantity_SufficientStock_ReturnsUpdatedSweet() throws Exception {
        // Arrange
        when(sweetService.purchaseSweet(1L, 3)).thenReturn(testSweet);

        // Act & Assert
        mockMvc.perform(post("/api/sweets/1/purchase?quantity=3")
)
                .andExpect(status().isOk())
;

        verify(sweetService).purchaseSweet(1L, 3);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void restockSweet_ValidQuantity_ReturnsUpdatedSweet() throws Exception {
        // Arrange
        when(sweetService.restockSweet(1L, 5)).thenReturn(testSweet);

        // Act & Assert
        mockMvc.perform(post("/api/sweets/1/restock?quantity=5")
)
                .andExpect(status().isOk())
;

        verify(sweetService).restockSweet(1L, 5);
    }


    @Test
    @WithMockUser
    void searchSweets_WithNameFilter_ReturnsFilteredResults() throws Exception {
        // Arrange
        List<Sweet> searchResults = Arrays.asList(testSweet);
        when(sweetService.searchSweets("Chocolate", null, null, null)).thenReturn(searchResults);

        // Act & Assert
        mockMvc.perform(get("/api/sweets/search?name=Chocolate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Chocolate Bar"));

        verify(sweetService).searchSweets("Chocolate", null, null, null);
    }

    @Test
    @WithMockUser
    void searchSweets_WithPriceRange_ReturnsFilteredResults() throws Exception {
        // Arrange
        List<Sweet> searchResults = Arrays.asList(testSweet);
        when(sweetService.searchSweets(null, null, 1.00, 5.00)).thenReturn(searchResults);

        // Act & Assert
        mockMvc.perform(get("/api/sweets/search?minPrice=1.00&maxPrice=5.00"))
                .andExpect(status().isOk())
;

        verify(sweetService).searchSweets(null, null, 1.00, 5.00);
    }



    @Configuration
    @EnableWebSecurity
    static class TestSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .headers(headers -> headers.disable());
            return http.build();
        }
    }
}
