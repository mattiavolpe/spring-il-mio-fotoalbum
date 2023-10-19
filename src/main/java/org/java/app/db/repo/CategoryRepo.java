package org.java.app.db.repo;

import org.java.app.db.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{
	public List<Category> findByNameContaining(String name);
}
