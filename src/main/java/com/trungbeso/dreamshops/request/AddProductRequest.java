package com.trungbeso.dreamshops.request;

import com.trungbeso.dreamshops.models.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
	private String name;

	private String brand;

	private BigDecimal price;

	private int inventory; //quantity

	private String description;

	private Category category;
}
