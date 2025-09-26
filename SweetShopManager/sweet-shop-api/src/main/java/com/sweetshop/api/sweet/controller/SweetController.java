package com.sweetshop.api.sweet.controller;

import com.sweetshop.api.sweet.model.Sweet;
import com.sweetshop.api.sweet.service.SweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    @Autowired
    private SweetService sweetService;

    // POST /api/sweets (Protected: Requires ADMIN role)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Sweet> addSweet(@Valid @RequestBody Sweet sweet) {
        Sweet newSweet = sweetService.saveSweet(sweet);
        return ResponseEntity.ok(newSweet);
    }

    // GET /api/sweets (Protected: Requires authentication)
    @GetMapping
    public ResponseEntity<List<Sweet>> getAllSweets() {
        List<Sweet> sweets = sweetService.findAllSweets();
        return ResponseEntity.ok(sweets);
    }

    // GET /api/sweets/search (Protected: Requires authentication)
    @GetMapping("/search")
    public ResponseEntity<List<Sweet>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        List<Sweet> sweets = sweetService.searchSweets(name, category, minPrice, maxPrice);
        return ResponseEntity.ok(sweets);
    }

    // GET /api/sweets/{id} (Protected: Requires authentication)
    @GetMapping("/{id}")
    public ResponseEntity<Sweet> getSweetById(@PathVariable Long id) {
        Optional<Sweet> sweet = sweetService.findById(id);
        return sweet.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/sweets/{id} (Protected: Requires ADMIN role)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Sweet> updateSweet(@PathVariable Long id, @Valid @RequestBody Sweet sweet) {
        try {
            Sweet updatedSweet = sweetService.updateSweet(id, sweet);
            return ResponseEntity.ok(updatedSweet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/sweets/{id} (Protected: Requires ADMIN role)
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id) {
        try {
            sweetService.deleteSweet(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/sweets/{id}/purchase (Protected: Requires USER or ADMIN)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/{id}/purchase")
    public ResponseEntity<Sweet> purchaseSweet(@PathVariable Long id , @RequestParam int quantity) {
        try {
            Sweet sweet = sweetService.purchaseSweet(id, quantity);
            return ResponseEntity.ok(sweet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // POST /api/sweets/{id}/restock (Protected: Requires ADMIN role)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/restock")
    public ResponseEntity<Sweet> restockSweet(@PathVariable Long id, @RequestParam int quantity) {
        try {
            Sweet sweet = sweetService.restockSweet(id, quantity);
            return ResponseEntity.ok(sweet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}