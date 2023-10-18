package org.java.app.db.service;

import java.util.List;

import org.java.app.db.auth.service.UserService;
import org.java.app.db.pojo.Photo;
import org.java.app.db.repo.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepo photoRepo;
	
	@Autowired
	private UserService userService;
	
	public void savePhoto(Photo photo) {
		photoRepo.save(photo);
	}
	
	public List<Photo> findAll() {
		return photoRepo.findAll();
	}
	
	public List<Photo> findUserPhotos(String username) {
		return photoRepo.findByUser(userService.findByUsername(username));
	}
	
	public List<Photo> findAllVisible() {
		return photoRepo.findByVisible(true);
	}
}
