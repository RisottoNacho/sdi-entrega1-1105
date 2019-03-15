package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Offer {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private Date date;
	private String description;
	private Double price;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private Boolean buyed = false;

	public Offer(Long id, String title, Date date, String description, Double price) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.date = date;
		this.title = title;
	}

	public Offer(String title, Date date, String description, Double price, User user) {
		super();
		this.description = description;
		this.price = price;
		this.date = date;
		this.title = title;
		this.user = user;
	}

	public Offer() {
	}

	public Boolean getResend() {
		return buyed;
	}

	public void setResend(Boolean resend) {
		this.buyed = resend;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getBuyed() {
		return buyed;
	}

	public void setBuyed(Boolean buyed) {
		this.buyed = buyed;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getScore() {
		return price;
	}

	public void setScore(Double score) {
		this.price = score;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}