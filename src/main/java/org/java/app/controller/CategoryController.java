package org.java.app.controller;

import java.util.List;
import java.util.Optional;

import org.java.app.db.pojo.Category;
import org.java.app.db.pojo.Photo;
import org.java.app.db.service.CategoryService;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categories/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping
	public String index(@RequestParam(required = false) String filter, Model model) {
		List<Category> categories = filter == null
				? categoryService.findAll()
				: categoryService.filterByName(filter);
		
		model.addAttribute("categories", categories);
		
		return "/category/index";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		Optional<Category> optCategory = categoryService.findById(id);
		
		if (optCategory.isEmpty())
			return "redirect:/";
		
		Category category = optCategory.get();
		
		for (Photo photo : category.getPhotos()) {
			photo.removeCategory(category);
			
			photoService.savePhoto(photo);
		}
		
		categoryService.delete(category);
		
		return "redirect:/categories/";
	}
}
