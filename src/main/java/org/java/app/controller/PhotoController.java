package org.java.app.controller;

import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("photos", photoService.findAll());
		return "/photo/index";
	}
}
