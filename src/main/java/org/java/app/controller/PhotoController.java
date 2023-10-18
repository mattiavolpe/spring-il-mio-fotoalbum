package org.java.app.controller;

import java.util.List;
import java.util.Optional;

import org.java.app.db.auth.pojo.User;
import org.java.app.db.pojo.Photo;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping
	public String index(@RequestParam(required = false) String filter, Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		if (user.getUsername().equals("superadmin")) {
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
	
	@GetMapping("/{id}")
	public String show(@PathVariable int id, Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		Optional<Photo> optPhoto = photoService.findById(id);
		
		if (optPhoto.isEmpty())
			return "redirect:/";
		
		Photo photo = optPhoto.get();
		int userId = user.getId();
		
		if (userId != 1 && userId != photo.getUser().getId())
			return "redirect:/";
		
		model.addAttribute("photo", photo);
		
		return "/photo/show";
	}
}
