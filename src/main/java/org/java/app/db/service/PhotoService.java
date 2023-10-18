package org.java.app.db.service;

import java.util.List;
import java.util.Optional;

import org.java.app.db.auth.pojo.User;
import org.java.app.db.pojo.Photo;
import org.java.app.db.repo.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepo photoRepo;
	
	public Photo savePhoto(Photo photo) {
		return photoRepo.save(photo);
	}
	
	public List<Photo> findAll() {
		return photoRepo.findAll();
	}
	
	public Optional<Photo> findById(int id) {
		return photoRepo.findById(id);
	}
	
	public List<Photo> findUserPhotos(User user) {
		return photoRepo.findByUser(user);
	}
	
	public List<Photo> findAllVisible() {
		return photoRepo.findByVisible(true);
	}
	
	public List<Photo> filterByTitleOrDescription(String title, String description) {
		return photoRepo.findByTitleContainingOrDescriptionContaining(title, description);
	}
	
	public List<Photo> filterByUserAndTitleOrDescription(User user, String title, String description) {
		return photoRepo.findByUserAndTitleContainingOrUserAndDescriptionContaining(user, title, user, description);
	}
	
	public void delete(Photo photo) {
		photoRepo.delete(photo);
	}
}
