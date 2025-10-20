package com.Ecommerce.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepository;

@RestController
@RequestMapping("/api/product")
public class ApiProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CartService cartService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderService orderService;
	

	
	@PostMapping("/products")
	public ResponseEntity<?> addProduct(@RequestBody Product p) {
	return ResponseEntity.ok(productService.addProduct(p));
	}


	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	productService.delete(id);
	return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public List<Product> listAll() { return productService.listAll(); }


	@GetMapping("/{id}")
	public Product get(@PathVariable Long id) { return productService.findById(id); }
	
	
	private User getCurrent(String email) {
		return userRepository.findByEmail(email).orElseThrow();
		}


		@GetMapping
		public List<CartItem> viewCart(@AuthenticationPrincipal UserDetails ud) {
		return cartService.getCart(getCurrent(ud.getUsername()));
		}


		@PostMapping("/add")
		public ResponseEntity<?> addToCart(@AuthenticationPrincipal UserDetails ud, @RequestBody AddReq req) {
		CartItem ci = cartService.addToCart(getCurrent(ud.getUsername()), req.variantId, req.quantity);
		return ResponseEntity.ok(ci);
		}


		@PostMapping("/clear")
		public ResponseEntity<?> clear(@AuthenticationPrincipal UserDetails ud) {
		cartService.clearCart(getCurrent(ud.getUsername()));
		return ResponseEntity.ok().build();
		}


		static class AddReq { public Long variantId; public Integer quantity; }
		
		
		@PostMapping("/place")
		public Order placeOrder(@AuthenticationPrincipal UserDetails ud) {
		User user = userRepository.findByEmail(ud.getUsername()).orElseThrow();
		List<CartItem> items = cartService.getCart(user);
		Order order = orderService.placeOrder(user, items);
		cartService.clearCart(user);
		return order;
		}
}
