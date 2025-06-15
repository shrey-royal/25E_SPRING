package com.royal.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.royal.core.SpringThymeleafApplication;
import com.royal.core.entity.Product;
import com.royal.core.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService service;
	
	public ProductController(ProductService service) {
		this.service = service;
	}
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("products", service.getAll());
		return "products/list";
	}
	
	@GetMapping("/add")
	public String showForm(Model model) {
		model.addAttribute("product", new Product());
		return "products/form";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Product product, BindingResult result) {
		if (result.hasErrors()) {
			return "products/form";
		}
		service.save(product);
		return "redirect:/products";
	}
	
	@GetMapping("/{id}")
	public String view(@PathVariable int id, Model model) {
		Product product = service.getById(id);
		if (product == null) {
			return "error/404";
		}
		model.addAttribute("product", product);
		return "products/details";
	}
}
