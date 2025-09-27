package com.sweetshop.api.sweet.repository;

import com.sweetshop.api.sweet.model.Sweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SweetRepositoryTest {

    @Autowired
    private SweetRepository sweetRepository;

    private Sweet chocolateBar;
    private Sweet vanillaIceCream;
    private Sweet strawberryCake;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        sweetRepository.deleteAll();

        // Create test sweets
        chocolateBar = new Sweet();
        chocolateBar.setName("Chocolate Bar");
        chocolateBar.setCategory("Chocolate");
        chocolateBar.setPrice(2.50);
        chocolateBar.setQuantityInStock(10);

        vanillaIceCream = new Sweet();
        vanillaIceCream.setName("Vanilla Ice Cream");
        vanillaIceCream.setCategory("Ice Cream");
        vanillaIceCream.setPrice(3.99);
        vanillaIceCream.setQuantityInStock(25);

        strawberryCake = new Sweet();
        strawberryCake.setName("Strawberry Cake");
        strawberryCake.setCategory("Cake");
        strawberryCake.setPrice(15.99);
        strawberryCake.setQuantityInStock(5);

        sweetRepository.save(chocolateBar);
        sweetRepository.save(vanillaIceCream);
        sweetRepository.save(strawberryCake);
    }

    @Test
    void findByNameContainingIgnoreCase_Found_ReturnsMatchingSweets() {
        // Act
        List<Sweet> results = sweetRepository.findByNameContainingIgnoreCase("chocolate");

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Chocolate Bar");
    }

    @Test
    void findByNameContainingIgnoreCase_NotFound_ReturnsEmptyList() {
        // Act
        List<Sweet> results = sweetRepository.findByNameContainingIgnoreCase("nonexistent");

        // Assert
        assertThat(results).isEmpty();
    }

    @Test
    void findByCategoryIgnoreCase_Found_ReturnsMatchingSweets() {
        // Act
        List<Sweet> results = sweetRepository.findByCategoryIgnoreCase("cake");

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Strawberry Cake");
    }

    @Test
    void findByCategoryIgnoreCase_CaseInsensitive_ReturnsMatchingSweets() {
        // Act
        List<Sweet> results = sweetRepository.findByCategoryIgnoreCase("CAKE");

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Strawberry Cake");
    }

    @Test
    void findByPriceBetween_InclusiveRange_ReturnsMatchingSweets() {
        // Act
        List<Sweet> results = sweetRepository.findByPriceBetween(2.00, 5.00);

        // Assert
        assertThat(results).hasSize(2);
        assertThat(results.stream().map(Sweet::getName))
                .contains("Chocolate Bar", "Vanilla Ice Cream");
    }

    @Test
    void findByPriceBetween_ExclusiveRange_ReturnsEmptyList() {
        // Act
        List<Sweet> results = sweetRepository.findByPriceBetween(50.00, 100.00);

        // Assert
        assertThat(results).isEmpty();
    }

    @Test
    void searchSweets_ByNameOnly_ReturnsMatchingSweets() {
        // Act
        List<Sweet> results = sweetRepository.searchSweets("Chocolate", null, null, null);

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Chocolate Bar");
    }

    @Test
    void searchSweets_ByCategoryOnly_ReturnsMatchingSweets() {
        // Act
        List<Sweet> results = sweetRepository.searchSweets(null, "Ice Cream", null, null);

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Vanilla Ice Cream");
    }

    @Test
    void searchSweets_ByPriceRangeOnly_ReturnsMatchingSweets() {
        // Act
        List<Sweet> results = sweetRepository.searchSweets(null, null, 10.00, 20.00);

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Strawberry Cake");
    }

    @Test
    void searchSweets_ByNameAndCategory_ReturnsMatchingSweets() {
        // Create another chocolate sweet
        Sweet chocolateCake = new Sweet();
        chocolateCake.setName("Chocolate Cake");
        chocolateCake.setCategory("Cake");
        chocolateCake.setPrice(12.99);
        chocolateCake.setQuantityInStock(8);
        sweetRepository.save(chocolateCake);

        // Act
        List<Sweet> results = sweetRepository.searchSweets("Chocolate", "Cake", null, null);

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Chocolate Cake");
    }

    @Test
    void searchSweets_CombinedFilters_ReturnsMatchingSweets() {
        // Act
        List<Sweet> results = sweetRepository.searchSweets(null, "Cake", 10.00, 20.00);

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Strawberry Cake");
        assertThat(results.get(0).getPrice()).isBetween(10.00, 20.00);
    }

    @Test
    void searchSweets_NoMatches_ReturnsEmptyList() {
        // Act
        List<Sweet> results = sweetRepository.searchSweets("Nonexistent", "Invalid", 100.00, 200.00);

        // Assert
        assertThat(results).isEmpty();
    }

    @Test
    void searchSweets_AllNullParameters_ReturnsAllSweets() {
        // Act
        List<Sweet> results = sweetRepository.searchSweets(null, null, null, null);

        // Assert
        assertThat(results).hasSize(3);
        assertThat(results.stream().map(Sweet::getName))
                .contains("Chocolate Bar", "Vanilla Ice Cream", "Strawberry Cake");
    }

    @Test
    void findAll_ReturnsAllSweets() {
        // Act
        List<Sweet> results = sweetRepository.findAll();

        // Assert
        assertThat(results).hasSize(3);
    }

    @Test
    void save_PersistsSweet_ReturnsSavedSweet() {
        // Arrange
        Sweet newSweet = new Sweet();
        newSweet.setName("New Candy");
        newSweet.setCategory("Candy");
        newSweet.setPrice(1.99);
        newSweet.setQuantityInStock(15);

        // Act
        Sweet saved = sweetRepository.save(newSweet);

        // Assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("New Candy");
        assertThat(sweetRepository.findAll()).hasSize(4);
    }

    @Test
    void findById_ExistingId_ReturnsSweet() {
        // Act
        var result = sweetRepository.findById(chocolateBar.getId());

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Chocolate Bar");
    }

    @Test
    void findById_NonExistingId_ReturnsEmpty() {
        // Act
        var result = sweetRepository.findById(999L);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void deleteById_RemovesSweet() {
        // Arrange
        Long idToDelete = chocolateBar.getId();

        // Act
        sweetRepository.deleteById(idToDelete);

        // Assert
        assertThat(sweetRepository.findById(idToDelete)).isEmpty();
        assertThat(sweetRepository.findAll()).hasSize(2);
    }
}
