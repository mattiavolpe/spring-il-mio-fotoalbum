package org.java.app.db.service;

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
}
