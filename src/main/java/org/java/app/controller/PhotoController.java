package org.java.app.controller;

import java.util.List;
import java.util.Optional;

import org.java.app.db.auth.pojo.User;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.github.slugify.Slugify;

import jakarta.validation.Valid;

@Controller
public class PhotoController {
	
	final Slugify slg = Slugify.builder().build();
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public String index(@RequestParam(required = false) String filter, Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		if (user.getId() == 1) {
			List<Photo> photos = filter == null
					? photoService.findAll()
					: photoService.filterByTitleOrDescription(filter, filter);
			
			model.addAttribute("photos", photos);
		} else {
			List<Photo> photos = filter == null
					? photoService.findUserPhotos(user)
					: photoService.filterByUserAndTitleOrDescription(user, filter, filter);
			
			model.addAttribute("photos", photos);
		}
		
		return "/photo/index";
	}
	
	@GetMapping("/{slug}")
	public String show(@PathVariable String slug, Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		Optional<Photo> optPhoto = photoService.findBySlug(slug);
		
		if (optPhoto.isEmpty())
			return "redirect:/";
		
		Photo photo = optPhoto.get();
		
		if (!checkPhotoOwnership(user, photo))
			return "redirect:/";
		
		model.addAttribute("photo", photo);
		
		return "/photo/show";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Photo photo = new Photo();
		
		photo.setVisible(true);
		photo.setHiddenBySuperadmin(false);
		
		model.addAttribute("photo", photo);
		model.addAttribute("categories", categoryService.findAll());
		
		return "/photo/create-update";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute Photo photo, BindingResult bindingResult, Model model, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAll());
			
			return "/photo/create-update";
		}
		
		photo.setUser((User) authentication.getPrincipal());
		photo.setHiddenBySuperadmin(false);
		photo.setSlug(slg.slugify(photo.getTitle()));
		
		Photo savedPhoto = photoService.savePhoto(photo);
		
		return "redirect:/" + savedPhoto.getSlug();
	}
	
	@GetMapping("/edit/{slug}")
	public String edit(@PathVariable String slug, Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		Optional<Photo> optPhoto = photoService.findBySlug(slug);
		
		if (optPhoto.isEmpty())
			return "redirect:/";
		
		Photo photo = optPhoto.get();
		
		if (!checkPhotoOwnership(user, photo))
			return "redirect:/";
		
		model.addAttribute("photo", photo);
		model.addAttribute("categories", categoryService.findAll());
		
		return "/photo/create-update";
	}
	
	@PostMapping("/edit/{slug}")
	public String update(@PathVariable String slug, @Valid @ModelAttribute Photo photo, BindingResult bindingResult, Model model, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAll());
			
			return "/photo/create-update";
		}
		
		Optional<Photo> optPhoto = photoService.findBySlug(slug);
		
		if (optPhoto.isEmpty())
			return "redirect:/";
		
		Photo originalPhoto = optPhoto.get();
		
		User user = originalPhoto.getUser();
		
		User authUser = (User) authentication.getPrincipal();

		photo.setId(originalPhoto.getId());
		photo.setUser(user);
		photo.setSlug(slg.slugify(photo.getTitle()));
		
		if (authUser.getId() == 1 && !photo.getVisible())
			photo.setHiddenBySuperadmin(true);
		else if (authUser.getId() != 1 && photo.getVisible() == null) {
			photo.setHiddenBySuperadmin(true);
			photo.setVisible(false);
		} else
			photo.setHiddenBySuperadmin(false);
		
		Photo savedPhoto = photoService.savePhoto(photo);
		
		return "redirect:/" + savedPhoto.getSlug();
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		Optional<Photo> optPhoto = photoService.findById(id);
		
		if (optPhoto.isEmpty())
			return "redirect:/";
		
		Photo photo = optPhoto.get();
		
		photoService.delete(photo);
		
		return "redirect:/";
	}
	
	private Boolean checkPhotoOwnership(User user, Photo photo) {
		int userId = user.getId();
		
		if (userId != 1 && userId != photo.getUser().getId())
			return false;
		
		return true;
	}
}
