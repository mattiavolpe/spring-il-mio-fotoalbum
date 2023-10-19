package org.java.app.db.pojo;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDateTime date;
	
	@NotBlank
	@Email
	@Column(nullable = false, length = 255)
	private String email;
	
	@Lob
	@Length(min = 3, max = 2000)
	@NotBlank
	@Column(columnDefinition = "text", nullable = false, length = 2000)
	private String content;
	
	public Message() {}

	public Message(LocalDateTime date, String email, String content) {
		setDate(date);
		setEmail(email);
		setContent(content);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
