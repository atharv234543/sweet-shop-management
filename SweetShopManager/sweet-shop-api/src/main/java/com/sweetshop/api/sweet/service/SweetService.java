package com.sweetshop.api.sweet.service;

import com.sweetshop.api.sweet.model.Sweet;
import com.sweetshop.api.sweet.repository.SweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SweetService {

	@Autowired
	private SweetRepository sweetRepository;

	public Sweet saveSweet(Sweet sweet) {
		if (sweet.getQuantityInStock() < 0 || sweet.getPrice() <= 0) {
			throw new IllegalArgumentException("Invalid sweet data.");
		}
		return sweetRepository.save(sweet);
	}

	public List<Sweet> findAllSweets() {
		return sweetRepository.findAll().stream()
			.sorted(Comparator.comparing(Sweet::getName))
			.collect(Collectors.toList());
	}

	public Optional<Sweet> findById(Long id) {
		return sweetRepository.findById(id);
	}

	@Transactional
	public Sweet updateSweet(Long id, Sweet updated) {
		Sweet existing = sweetRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Sweet not found with ID: " + id));
		if (updated.getName() != null) existing.setName(updated.getName());
		if (updated.getCategory() != null) existing.setCategory(updated.getCategory());
		if (updated.getPrice() != null && updated.getPrice() > 0) existing.setPrice(updated.getPrice());
		if (updated.getQuantityInStock() != null && updated.getQuantityInStock() >= 0) existing.setQuantityInStock(updated.getQuantityInStock());
		return sweetRepository.save(existing);
	}

	public void deleteSweet(Long id) {
		if (!sweetRepository.existsById(id)) {
			throw new RuntimeException("Sweet not found with ID: " + id);
		}
		sweetRepository.deleteById(id);
	}

	public List<Sweet> searchSweets(String name, String category, Double minPrice, Double maxPrice) {
		return sweetRepository.searchSweets(name, category, minPrice, maxPrice).stream()
			.sorted(Comparator.comparing(Sweet::getName))
			.collect(Collectors.toList());
	}

	@Transactional
	public Sweet purchaseSweet(Long id, int quantity) {
		Sweet sweet = sweetRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Sweet not found with ID: " + id));

		if (sweet.getQuantityInStock() <= 0 || quantity>sweet.getQuantityInStock()) {
			throw new RuntimeException("Sweet is out of stock!");
		}

		sweet.setQuantityInStock(sweet.getQuantityInStock() - quantity);
		return sweetRepository.save(sweet);
	}

	@Transactional
	public Sweet restockSweet(Long id, int quantity) {
		Sweet sweet = sweetRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Sweet not found with ID: " + id));

		if (quantity <= 0) {
			throw new IllegalArgumentException("Restock quantity must be positive.");
		}

		sweet.setQuantityInStock(sweet.getQuantityInStock() + quantity);
		return sweetRepository.save(sweet);
	}
}