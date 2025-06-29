package com.royal.core.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.royal.core.entity.Customer;
import com.royal.core.entity.Order;
import com.royal.core.entity.Product;
import com.royal.core.service.CustomerService;
import com.royal.core.service.OrderService;
import com.royal.core.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	private final CustomerService customerService;
	private final ProductService productService;
	
	@GetMapping
	public String listOrders(Model model) {
		model.addAttribute("orders", orderService.getAllOrders());
		return "orders/list";
	}
	
	@GetMapping("/create")
	public String showOrderForm(Model model) {
		model.addAttribute("order", new Order());
		model.addAttribute("customers", customerService.getAllCustomers());
		model.addAttribute("products", productService.getAllProducts());
		return "orders/form";
	}
	
	@PostMapping("/create")
	public String createOrder(@ModelAttribute Order order, @RequestParam List<Long> productIds) {
		Customer customer = customerService.getCustomerById(order.getCustomer().getId()).orElseThrow();
		List<Product> products = productIds.stream()
				.map(productService::getProductById)
				.map(Optional::orElseThrow)
				.toList();
		
		order.setCustomer(customer);
		order.setProducts(products);
		order.setOrderDate(LocalDateTime.now());
		
		orderService.saveOrder(order);
		return "redirect:/orders";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return "redirect:/orders";
	}
}