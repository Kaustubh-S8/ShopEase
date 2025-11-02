package com.Ecommerce.Product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Ecommerce.Exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final ProductVariantRepository variantRepository;

	public ProductService(ProductRepository productRepository, ProductVariantRepository variantRepository) {
		this.productRepository = productRepository;
		this.variantRepository = variantRepository;
	}

	public Product addProduct(ProductRequest req) {
// variants should set product reference
//		if (product.getVariants() != null) {
//			for (ProductVariant v : product.getVariants())
//				v.setProduct(product);
//		}
//		return productRepository.save(product);
//	}
//
//	public List<Product> listAll() {
//		return productRepository.findAll();
//	}
//
//	public Product findById(Long id) {
//		return productRepository.findById(id).orElseThrow();
//	}
//
//	public void delete(Long id) {
//		productRepository.deleteById(id);
//	}
		Product p = Product.builder()
				.name(req.getName())
				.description(req.getDescription())
				.price(req.getPrice())
				.category(req.getCategory())
				.imageUrl(req.getImageUrl())
				.build();


				List<ProductVariant> variants = req.getVariants().stream().map(v -> {
				ProductVariant pv = ProductVariant.builder()
				.color(v.getColor())
				.size(v.getSize())
				.quantity(v.getQuantity())
				.product(p)
				.build();
				return pv;
				}).collect(Collectors.toList());


				p.getVariants().addAll(variants);
				return productRepository.save(p);
				}


				public List<Product> listAll() { return productRepository.findAll(); }
				public Product findById(Long id) { return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found")); }
				public void delete(Long id) { productRepository.deleteById(id); }
}