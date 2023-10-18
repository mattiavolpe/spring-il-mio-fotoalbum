package org.java.app.db.pojo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;
import org.java.app.db.auth.pojo.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
	private User user;
	
	@ManyToMany
	private List<Category> categories;
	
	public Photo() {}
	
	public Photo(String title, String description, String url, Boolean visible, User user, Category...categories) {
		setTitle(title);
		setDescription(description);
		setUrl(url);
		setVisible(visible);
		setUser(user);
		setCategories(Arrays.asList(categories));
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	@Override
	public String toString() {
		return "[" + getId() + "] - " + getTitle() + "\n" + getDescription() + "\n" + getUrl() + "\n" + (getVisible() ? "Visible" : "Invisible") + "\n" + getCategories();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (!(obj instanceof Photo)) return false;
		
		Photo extObj = (Photo) obj;
		
		return getId() == extObj.getId();
	}
}
