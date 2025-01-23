package com.trungbeso.dreamshops.dtos;

import com.trungbeso.dreamshops.models.Category;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
	Long id;

	String name;

	String brand;

	BigDecimal price;

	int inventory; //quantity

	String description;

	Category category;

	List<ImageDto> images;
}
