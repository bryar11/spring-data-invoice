package com.javausergroupcr.springdata.app.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String code;

	@NotEmpty
	@Column(length = 50)
	private String name;

	private double price;

	private boolean enabled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	@JsonIgnore
	private DBUser createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	@JsonIgnore
	private DBUser updatedBy;

	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}

	private static final long serialVersionUID = 1L;

}
