package com.Ecommerce.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Ecommerce.Exceptions.ResourceNotFoundException;
import com.Ecommerce.User.User;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final ProductVariantRepository variantRepository;

	public OrderService(OrderRepository orderRepository, ProductVariantRepository variantRepository) {
		this.orderRepository = orderRepository;
		this.variantRepository = variantRepository;
	}

//	public Order placeOrder(User user, List<CartItem> items) {
//		Order order = new Order();
//		order.setUser(user);
//		order.setPlacedAt(Instant.now());
//
//		BigDecimal total = BigDecimal.ZERO;
//
//		for (CartItem ci : items) {
//			ProductVariant v = variantRepository.findById(ci.getVariant().getId()).orElseThrow();
//			if (v.getQuantity() < ci.getQuantity()) {
//				throw new RuntimeException("Not enough stock for variant " + v.getId());
//			}
//
//			v.setQuantity(v.getQuantity() - ci.getQuantity());
//			variantRepository.save(v);
//
//			OrderItem oi = OrderItem.builder().variant(v).quantity(ci.getQuantity())
//					.priceAtPurchase(v.getProduct().getPrice()).build();
//			oi.setOrder(order);
//			order.getItems().add(oi);
//
//			total = total.add(v.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
//		}
//
//		order.setTotalAmount(total);
//		return orderRepository.save(order);
//	}

	public Order placeOrder(User user, List<CartItem> items) {
		Order order = new Order();
		order.setUser(user);
		order.setPlacedAt(Instant.now());

		BigDecimal total = BigDecimal.ZERO;

		for (CartItem ci : items) {
			ProductVariant v = variantRepository.findById(ci.getVariant().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
			if (v.getQuantity() < ci.getQuantity()) {
				throw new RuntimeException("Not enough stock for variant " + v.getId());
			}

			v.setQuantity(v.getQuantity() - ci.getQuantity());
			variantRepository.save(v);

			OrderItem oi = OrderItem.builder().variant(v).quantity(ci.getQuantity())
					.priceAtPurchase(v.getProduct().getPrice()).build();
			oi.setOrder(order);
			order.getItems().add(oi);

			total = total.add(v.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
		}

		order.setTotalAmount(total);
		return orderRepository.save(order);
	}
}