package org.java.app.db.repo;

import java.util.List;
import java.util.Optional;

import org.java.app.db.auth.pojo.User;
import org.java.app.db.pojo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PhotoRepo extends JpaRepository<Photo, Integer> {
	public List<Photo> findByVisible(Boolean visible);
	public List<Photo> findByVisibleAndTitleContainingOrVisibleAndDescriptionContaining(Boolean visible, String title, Boolean visible2, String description);
	public List<Photo> findByUser(User user);
	public List<Photo> findByTitleContainingOrDescriptionContaining(String title, String description);
	public List<Photo> findByUserAndTitleContainingOrUserAndDescriptionContaining(User user, String title, User user2, String description);
	public Optional<Photo> findBySlug(String slug);
}
