package org.java.app.api.controller;

import java.util.List;

import org.java.app.db.pojo.Photo;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class PhotoRestController {
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping
	public ResponseEntity<List<Photo>> index(@RequestParam(required = false) String filter) {
		List<Photo> photos = filter == null
				? photoService.findAllVisible()
				: photoService.filterByVisibleAndTitleOrDescription(filter);
		
		return new ResponseEntity<List<Photo>>(photos, HttpStatus.OK);
	}
}
