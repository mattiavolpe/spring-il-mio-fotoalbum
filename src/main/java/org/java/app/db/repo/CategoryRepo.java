package org.java.app.db.repo;

import java.util.List;
import java.util.Optional;

import org.java.app.db.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{
	public Optional<Category> findById(int id);
	public List<Category> findByNameContaining(String name);
}
