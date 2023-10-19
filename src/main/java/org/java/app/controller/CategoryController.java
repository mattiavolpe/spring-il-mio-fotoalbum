package org.java.app.controller;

import java.util.List;

import org.java.app.db.pojo.Category;
import org.java.app.db.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categories/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public String index(@RequestParam(required = false) String filter, Model model) {
		List<Category> categories = filter == null
				? categoryService.findAll()
				: categoryService.filterByName(filter);
		
		model.addAttribute("categories", categories);
		
		return "/category/index";
	}
}
