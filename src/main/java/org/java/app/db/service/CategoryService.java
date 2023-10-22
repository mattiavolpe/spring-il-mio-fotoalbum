package org.java.app.db.service;

import java.util.List;
import java.util.Optional;

import org.java.app.db.pojo.Category;
import org.java.app.db.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	public void saveCategory(Category category) {
		categoryRepo.save(category);
	}
	
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}
	
	public Optional<Category> findById(int id) {
		return categoryRepo.findById(id);
	}
	
	public Optional<Category> findBySlug(String slug) {
		return categoryRepo.findBySlug(slug);
	}
	
	public List<Category> filterByName(String name) {
		return categoryRepo.findByNameContaining(name);
	}
	
	public void delete(Category category) {
		categoryRepo.delete(category);
	}
}
