package com.sweetshop.api.sweet.service;

import com.sweetshop.api.sweet.model.Sweet;
import com.sweetshop.api.sweet.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private SweetService sweetService;

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
    void saveSweet_ValidSweet_ReturnsSavedSweet() {
        // Arrange
        when(sweetRepository.save(any(Sweet.class))).thenReturn(testSweet);

        // Act
        Sweet result = sweetService.saveSweet(testSweet);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Chocolate Bar");
        assertThat(result.getPrice()).isEqualTo(2.50);
        verify(sweetRepository).save(testSweet);
    }

    @Test
    void saveSweet_InvalidPrice_ThrowsException() {
        // Arrange
        testSweet.setPrice(-1.0);

        // Act & Assert
        assertThatThrownBy(() -> sweetService.saveSweet(testSweet))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid sweet data");
    }

    @Test
    void saveSweet_InvalidStock_ThrowsException() {
        // Arrange
        testSweet.setQuantityInStock(-5);

        // Act & Assert
        assertThatThrownBy(() -> sweetService.saveSweet(testSweet))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid sweet data");
    }

    @Test
    void findAllSweets_ReturnsSortedList() {
        // Arrange
        Sweet sweet2 = new Sweet();
        sweet2.setId(2L);
        sweet2.setName("Apple Candy");
        sweet2.setCategory("Candy");
        sweet2.setPrice(1.00);
        sweet2.setQuantityInStock(5);

        when(sweetRepository.findAll()).thenReturn(Arrays.asList(testSweet, sweet2));

        // Act
        List<Sweet> result = sweetService.findAllSweets();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Apple Candy"); // Sorted alphabetically
        assertThat(result.get(1).getName()).isEqualTo("Chocolate Bar");
    }

    @Test
    void findById_ExistingId_ReturnsSweet() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(testSweet));

        // Act
        Optional<Sweet> result = sweetService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Chocolate Bar");
    }

    @Test
    void findById_NonExistingId_ReturnsEmpty() {
        // Arrange
        when(sweetRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Sweet> result = sweetService.findById(99L);

        // Assert
        assertThat(result).isEmpty();
    }



    @Test
    void purchaseSweet_WithQuantity_SufficientStock_ReturnsUpdatedSweet() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(testSweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(testSweet);

        // Act
        Sweet result = sweetService.purchaseSweet(1L, 3);

        // Assert
        assertThat(result).isNotNull();
        assertThat(testSweet.getQuantityInStock()).isEqualTo(7); // Stock decreased by 3
        verify(sweetRepository).save(testSweet);
    }

    @Test
    void purchaseSweet_WithQuantity_InsufficientStock_ThrowsException() {
        // Arrange
        testSweet.setQuantityInStock(5);
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(testSweet));

        // Act & Assert
        assertThatThrownBy(() -> sweetService.purchaseSweet(1L, 8))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sweet is out of stock");
    }

    @Test
    @Transactional
    void restockSweet_ValidQuantity_ReturnsUpdatedSweet() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(testSweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(testSweet);

        // Act
        Sweet result = sweetService.restockSweet(1L, 5);

        // Assert
        assertThat(result).isNotNull();
        assertThat(testSweet.getQuantityInStock()).isEqualTo(15); // Stock increased by 5
        verify(sweetRepository).save(testSweet);
    }

    @Test
    void restockSweet_InvalidQuantity_ThrowsException() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(testSweet));

        // Act & Assert
        assertThatThrownBy(() -> sweetService.restockSweet(1L, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Restock quantity must be positive");
    }

    @Test
    void restockSweet_NonExistingId_ThrowsException() {
        // Arrange
        when(sweetRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> sweetService.restockSweet(99L, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sweet not found with ID: 99");
    }

    @Test
    void updateSweet_ExistingId_ReturnsUpdatedSweet() {
        // Arrange
        Sweet updateData = new Sweet();
        updateData.setName("Updated Chocolate Bar");
        updateData.setPrice(3.00);

        when(sweetRepository.findById(1L)).thenReturn(Optional.of(testSweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(testSweet);

        // Act
        Sweet result = sweetService.updateSweet(1L, updateData);

        // Assert
        assertThat(result).isNotNull();
        assertThat(testSweet.getName()).isEqualTo("Updated Chocolate Bar");
        assertThat(testSweet.getPrice()).isEqualTo(3.00);
        verify(sweetRepository).save(testSweet);
    }

    @Test
    void updateSweet_NonExistingId_ThrowsException() {
        // Arrange
        Sweet updateData = new Sweet();
        updateData.setName("Updated Name");

        when(sweetRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> sweetService.updateSweet(99L, updateData))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sweet not found with ID: 99");
    }

    @Test
    void deleteSweet_ExistingId_DeletesSuccessfully() {
        // Arrange
        when(sweetRepository.existsById(1L)).thenReturn(true);

        // Act
        sweetService.deleteSweet(1L);

        // Assert
        verify(sweetRepository).deleteById(1L);
    }

    @Test
    void deleteSweet_NonExistingId_ThrowsException() {
        // Arrange
        when(sweetRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> sweetService.deleteSweet(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sweet not found with ID: 99");
    }
}
