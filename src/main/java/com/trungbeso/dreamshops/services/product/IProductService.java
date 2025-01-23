package com.trungbeso.dreamshops.services.product;

import com.trungbeso.dreamshops.dtos.ProductDto;
import com.trungbeso.dreamshops.models.Product;
import com.trungbeso.dreamshops.request.AddProductRequest;
import com.trungbeso.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
	Product addProduct(AddProductRequest product);

	Product getProductById(Long id);

	void deleteProductById(Long id);

	Product updateProduct(ProductUpdateRequest request, Long productId);

	List<Product> getAllProducts();

	List<Product> getProductsByCategory(String category);

	List<Product> getProductByBrand(String brand);

	List<Product> getProductByCategoryAndBrand(String category, String brand);

	List<Product> getProductByName(String name);

	List<Product> getProductByBrandAndName(String brand, String name);

	Long countProductsByBrandAndName(String brand, String name);

	List<ProductDto> getConvertedProducts(List<Product> products);

	ProductDto convertToDto(Product product);
}
