package com.Ecommerce.Product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Ecommerce.Exceptions.ResourceNotFoundException;
import com.Ecommerce.User.User;

@Service
public class CartService {
	private final CartItemRepository cartItemRepository;
	private final ProductVariantRepository variantRepository;

	public CartService(CartItemRepository cartItemRepository, ProductVariantRepository variantRepository) {
		this.cartItemRepository = cartItemRepository;
		this.variantRepository = variantRepository;
	}

//	public List<CartItem> getCart(User user) {
//		return cartItemRepository.findByUser(user);
//	}
//
//	public CartItem addToCart(User user, Long variantId, Integer qty) {
//		ProductVariant v = variantRepository.findById(variantId).orElseThrow();
//		CartItem ci = CartItem.builder().user(user).variant(v).quantity(qty).build();
//		return cartItemRepository.save(ci);
//	}
//
//	public void clearCart(User user) {
//		cartItemRepository.deleteByUser(user);
//	}

	public List<CartItem> getCart(User user) {
		return cartItemRepository.findByUser(user);
	}

	public CartItem addToCart(User user, Long variantId, Integer qty) {
		ProductVariant v = variantRepository.findById(variantId)
				.orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
		if (v.getQuantity() < qty)
			throw new RuntimeException("Not enough stock");
		CartItem ci = CartItem.builder().user(user).variant(v).quantity(qty).build();
		return cartItemRepository.save(ci);
	}

	public void clearCart(User user) {
		cartItemRepository.deleteByUser(user);
	}
}