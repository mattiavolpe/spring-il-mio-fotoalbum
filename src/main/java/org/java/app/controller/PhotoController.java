package org.java.app.controller;

import java.util.List;

import org.java.app.db.auth.pojo.User;
import org.java.app.db.pojo.Photo;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping
	public String index(Model model, @RequestParam(required = false) String filter, Authentication authentication) {
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
}
