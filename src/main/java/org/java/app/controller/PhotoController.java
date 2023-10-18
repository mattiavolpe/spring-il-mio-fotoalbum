package org.java.app.controller;

import org.java.app.db.auth.service.UserService;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String index(Model model) {
		String loggedUsername = userService.findAuthenticatedUser();
		
		if (loggedUsername.equals("superadmin")) {
			model.addAttribute("photos", photoService.findAll());			
		} else if (loggedUsername.equals("anonymousUser")) {
			return "redirect:/login";			
		} else {
			model.addAttribute("photos", photoService.findUserPhotos(loggedUsername));			
		}
		
		return "/photo/index";
	}
}
