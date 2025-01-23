package com.trungbeso.dreamshops.repositories;

import com.trungbeso.dreamshops.models.Category;
import com.trungbeso.dreamshops.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByCategoryName(String category);

	List<Product> findByBrand(String brand);

	List<Product> findByCategoryNameAndBrand(String category, String brand);

	List<Product> findByName(String name);

	List<Product> findByBrandAndBrand(String brand, String name);

	Long countByBrandAndName(String brand, String name);
}
