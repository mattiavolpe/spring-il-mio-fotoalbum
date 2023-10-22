package org.java.app.controller;

import java.util.List;
import java.util.Optional;

import org.java.app.db.auth.pojo.User;
import org.java.app.db.pojo.Category;
import org.java.app.db.pojo.Photo;
import org.java.app.db.service.CategoryService;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.slugify.Slugify;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	
	final Slugify slg = Slugify.builder().build();

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
	
	@GetMapping("/{slug}")
	public String show(@PathVariable String slug, Model model, Authentication authentication) {		
		Optional<Category> optCategory = categoryService.findBySlug(slug);
		
		if (optCategory.isEmpty())
			return "redirect:/categories";
		
		Category category = optCategory.get();
		
		User user = (User) authentication.getPrincipal();
		
		List<Photo> photos = user.getId() == 1 ? category.getPhotos() : category.getPhotos().stream().filter(photo -> photo.getUser().getId() == user.getId()).toList();
		
		model.addAttribute("category", category);
		model.addAttribute("photos", photos);
		
		return "/category/show";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Category category = new Category();
		
		model.addAttribute("category", category);
		
		model.addAttribute("photos", photoService.findAll());
		
		return "/category/create-update";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute Category category, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAll());
			
			return "/photo/create-update";
		}
		
		category.setSlug(slg.slugify(category.getName()));
		
		categoryService.saveCategory(category);
		
		if (category.getPhotos() != null) {
			
			for (Photo photo : category.getPhotos()) {
				photo.addCategory(category);
				
				photoService.savePhoto(photo);
			}
		}
		
		return "redirect:/categories";
	}
	
	@GetMapping("/edit/{slug}")
	public String edit(@PathVariable String slug, Model model) {		
		Optional<Category> optCategory = categoryService.findBySlug(slug);
		
		if (optCategory.isEmpty())
			return "redirect:/categories";
		
		Category category = optCategory.get();
		
		model.addAttribute("category", category);
		model.addAttribute("photos", photoService.findAll());
		
		return "/category/create-update";
	}
	
	@PostMapping("/edit/{slug}")
	public String update(@PathVariable String slug, @Valid @ModelAttribute Category category, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAll());
			
			return "/photo/create-update";
		}
		
		Optional<Category> optCategory = categoryService.findBySlug(slug);
		
		if (optCategory.isEmpty())
			return "redirect:/categories";
		
		Category originalCategory = optCategory.get();
		
		category.setId(originalCategory.getId());
		category.setSlug(slg.slugify(category.getName()));
		
		for (Photo photo : photoService.findAll()) {
			if (category.hasPhoto(photo) && !originalCategory.hasPhoto(photo)) {
				photo.addCategory(category);
			} else if (!category.hasPhoto(photo)) {
				photo.removeCategory(category);
			}
			
			photoService.savePhoto(photo);
		}
		
		Category savedCategory = categoryService.saveCategory(category);
		
		return "redirect:/categories/" + savedCategory.getSlug();
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		Optional<Category> optCategory = categoryService.findById(id);
		
		if (optCategory.isEmpty())
			return "redirect:/categories";
		
		Category category = optCategory.get();
		
		for (Photo photo : category.getPhotos()) {
			photo.removeCategory(category);
			
			photoService.savePhoto(photo);
		}
		
		categoryService.delete(category);
		
		return "redirect:/categories";
	}
}
