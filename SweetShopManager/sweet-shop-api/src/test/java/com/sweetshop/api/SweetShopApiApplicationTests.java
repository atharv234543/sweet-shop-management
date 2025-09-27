package com.sweetshop.api;

import com.sweetshop.api.sweet.model.Sweet;
import com.sweetshop.api.sweet.repository.SweetRepository;
import com.sweetshop.api.user.model.User;
import com.sweetshop.api.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SweetShopApiApplicationTests {

    @Autowired
    private SweetRepository sweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
        assertThat(sweetRepository).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    void databaseConnection_Works() {
        // Test database connectivity by saving and retrieving entities

        // Create test user
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setRole("USER");

        User savedUser = userRepository.save(testUser);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");

        // Create test sweet
        Sweet testSweet = new Sweet();
        testSweet.setName("Test Chocolate");
        testSweet.setCategory("Chocolate");
        testSweet.setPrice(2.99);
        testSweet.setQuantityInStock(50);

        Sweet savedSweet = sweetRepository.save(testSweet);
        assertThat(savedSweet.getId()).isNotNull();
        assertThat(savedSweet.getName()).isEqualTo("Test Chocolate");
        assertThat(savedSweet.getQuantityInStock()).isEqualTo(50);

        // Test relationships and queries
        assertThat(sweetRepository.findAll()).hasSizeGreaterThanOrEqualTo(1);
        assertThat(userRepository.findByUsername("testuser")).isPresent();

        // Cleanup
        sweetRepository.delete(savedSweet);
        userRepository.delete(savedUser);
    }

    @Test
    void jpaQueries_WorkCorrectly() {
        // Test custom JPA queries

        // Create test data
        Sweet sweet1 = new Sweet();
        sweet1.setName("Vanilla Ice Cream");
        sweet1.setCategory("Ice Cream");
        sweet1.setPrice(3.50);
        sweet1.setQuantityInStock(20);

        Sweet sweet2 = new Sweet();
        sweet2.setName("Chocolate Cake");
        sweet2.setCategory("Cake");
        sweet2.setPrice(15.99);
        sweet2.setQuantityInStock(5);

        sweetRepository.save(sweet1);
        sweetRepository.save(sweet2);

        // Test custom queries
        assertThat(sweetRepository.findByNameContainingIgnoreCase("chocolate"))
                .hasSizeGreaterThanOrEqualTo(1)
                .anyMatch(sweet -> sweet.getName().toLowerCase().contains("chocolate"));

        assertThat(sweetRepository.findByCategoryIgnoreCase("cake"))
                .hasSizeGreaterThanOrEqualTo(1)
                .anyMatch(sweet -> sweet.getCategory().equalsIgnoreCase("cake"));

        assertThat(sweetRepository.findByPriceBetween(1.00, 10.00))
                .hasSizeGreaterThanOrEqualTo(1);

        // Cleanup
        sweetRepository.delete(sweet1);
        sweetRepository.delete(sweet2);
    }
}
