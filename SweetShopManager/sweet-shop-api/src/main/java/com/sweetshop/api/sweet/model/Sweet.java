// sweet-shop-api/src/main/java/com/sweetshop/api/sweet/model/Sweet.java
package com.sweetshop.api.sweet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "sweets")
public class Sweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String name;

	@NotBlank
	private String category;

	@NotNull
	@Min(0)
	private Double price;

	@Column(name = "quantity_in_stock")
	@NotNull
	@Min(0)
	private Integer quantityInStock;
}