package com.Ecommerce.Product;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final ProductVariantRepository variantRepository;

	public ProductService(ProductRepository productRepository, ProductVariantRepository variantRepository) {
		this.productRepository = productRepository;
		this.variantRepository = variantRepository;
	}

	public Product addProduct(Product product) {
// variants should set product reference
		if (product.getVariants() != null) {
			for (ProductVariant v : product.getVariants())
				v.setProduct(product);
		}
		return productRepository.save(product);
	}

	public List<Product> listAll() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		return productRepository.findById(id).orElseThrow();
	}

	public void delete(Long id) {
		productRepository.deleteById(id);
	}
}