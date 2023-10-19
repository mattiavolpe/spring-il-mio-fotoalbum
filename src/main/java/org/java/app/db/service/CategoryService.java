package org.java.app.db.service;

import java.util.List;

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
	
	public List<Category> filterByName(String name) {
		return categoryRepo.findByNameContaining(name);
	}
}
