package org.java.app.db.repo;

import org.java.app.db.pojo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.java.app.db.auth.pojo.User;



@Repository
public interface PhotoRepo extends JpaRepository<Photo, Integer> {
	public List<Photo> findByVisible(Boolean visible);
	public List<Photo> findByUser(User user);
	public List<Photo> findByTitleContainingOrDescriptionContaining(String title, String description);
	public List<Photo> findByUserAndTitleContainingOrUserAndDescriptionContaining(User user, String title, User user2, String description);
}
