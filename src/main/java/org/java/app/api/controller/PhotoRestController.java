package org.java.app.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.java.app.db.pojo.Message;
import org.java.app.db.pojo.Photo;
import org.java.app.db.service.MessageService;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class PhotoRestController {
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping
	public ResponseEntity<List<Photo>> index(@RequestParam(required = false) String filter) {
		List<Photo> photos = filter == null
				? photoService.findAllVisible()
				: photoService.filterByVisibleAndTitleOrDescription(filter);
		
		return new ResponseEntity<List<Photo>>(photos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Photo> show(@PathVariable int id) {
		Optional<Photo> optPhoto = photoService.findById(id);
		
		if (optPhoto.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
		Photo photo = optPhoto.get();
		
		if (!photo.getVisible())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Photo>(optPhoto.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Boolean> sendMessage(@RequestBody Message message) {
		message.setDate(LocalDateTime.now());
		messageService.saveMessage(message);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
