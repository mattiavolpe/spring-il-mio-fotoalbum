package org.java.app.db.pojo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;
import org.java.app.db.auth.pojo.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.slugify.Slugify;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Photo {
	
	@Transient
	final Slugify slg = Slugify.builder().build();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "The title is required")
	@Length(max = 255)
	@Column(unique = true, nullable = false)
	private String title;
	
	@Length(max = 255)
	@Column(unique = true, nullable = false)
	private String slug;
	
	@Length(max = 2000, message = "The description can have a maximum of 2000 characters")
	@Lob
	@Column(columnDefinition = "text")
	private String description;
	
	@Length(max = 2000, message = "The URL can have a maximum of 2000 characters")
	@NotBlank(message = "The URL is required")
	@Lob
	@Column(columnDefinition = "text", nullable = false)
	private String url;
	
	@Column(nullable = false)
	private Boolean visible;
	
	@Column(nullable = false)
	private Boolean hiddenBySuperadmin;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonManagedReference
	private User user;
	
	@ManyToMany
	@JsonManagedReference
	private List<Category> categories;
	
	public Photo() {}
	
	public Photo(String title, String description, String url, Boolean visible, Boolean hiddenBySuperadmin, User user, Category...categories) {
		setTitle(title);
		setSlug(slg.slugify(title));
		setDescription(description);
		setUrl(url);
		setVisible(visible);
		setHiddenBySuperadmin(hiddenBySuperadmin);
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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
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

	public Boolean getHiddenBySuperadmin() {
		return hiddenBySuperadmin;
	}

	public void setHiddenBySuperadmin(Boolean hiddenBySuperadmin) {
		this.hiddenBySuperadmin = hiddenBySuperadmin;
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
	
	public void addCategory(Category category) {
		getCategories().add(category);
	}
	
	public void removeCategory(Category category) {
		getCategories().remove(category);
	}
	
	@Override
	public String toString() {
		return "[" + getId() + "] - " + getTitle() + "\nDescription: " + getDescription() + "\nUrl: " + getUrl() + "\nVisible: " + getVisible() + "\n" + getCategories();
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
