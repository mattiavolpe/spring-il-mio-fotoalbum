package org.java.app.controller;

import java.util.List;

import org.java.app.db.auth.service.UserService;
import org.java.app.db.pojo.Photo;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String index(Model model, @RequestParam(required = false) String filter) {
		String loggedUsername = userService.findAuthenticatedUser();
		
		if (loggedUsername.equals("superadmin")) {
			List<Photo> photos = filter == null
					? photoService.findAll()
					: photoService.filterByTitleOrDescription(filter, filter);
			
			model.addAttribute("photos", photos);			
		} else if (loggedUsername.equals("anonymousUser")) {
			return "redirect:/login";			
		} else {
			List<Photo> photos = filter == null
					? photoService.findUserPhotos(loggedUsername)
					: photoService.filterByUserAndTitleOrDescription(userService.findByUsername(loggedUsername), filter, filter);
			
			model.addAttribute("photos", photos);
		}
		
		return "/photo/index";
	}
}
