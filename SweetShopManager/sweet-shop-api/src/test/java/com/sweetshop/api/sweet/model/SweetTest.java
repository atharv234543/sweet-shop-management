package com.sweetshop.api.sweet.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SweetTest {

    @Test
    void testSweetCreation() {
        Sweet sweet = new Sweet();
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(2.50);
        sweet.setQuantityInStock(10);

        assertEquals("Chocolate Bar", sweet.getName());
        assertEquals("Chocolate", sweet.getCategory());
        assertEquals(2.50, sweet.getPrice());
        assertEquals(10, sweet.getQuantityInStock());
    }

    @Test
    void testSweetValidation() {
        Sweet sweet = new Sweet();
        sweet.setName("Test Sweet");
        sweet.setCategory("Test Category");
        sweet.setPrice(5.00);
        sweet.setQuantityInStock(5);

        // Test positive price and stock
        assertTrue(sweet.getPrice() > 0);
        assertTrue(sweet.getQuantityInStock() >= 0);
    }
}
