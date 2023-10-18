package org.java.app.db.pojo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale.Category;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Photo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "The title is required")
	@Column(unique = true, nullable = false)
	private String title;
	
	@Length(max = 2000, message = "The description can have a maximum of 2000 characters")
	@Lob
	@Column(columnDefinition = "text")
	private String description;
	
	@Length(max = 2000, message = "The URL can have a maximum of 2000 characters")
	@NotBlank(message = "The URL is required")
	@Lob
	@Column(columnDefinition = "text", nullable = false)
	private String url;
	
	private Boolean visible;
	
	@ManyToMany
	private Set<Category> categories;
	
	public Photo() {}
	
	public Photo(String title, String description, String url, Boolean visible, Category...categories) {
		setTitle(title);
		setDescription(description);
		setUrl(url);
		setVisible(visible);
		setCategories(new HashSet<Category>(Arrays.asList(categories)));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
}
