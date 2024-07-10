package com.app.cursos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

import lombok.ToString;

@Entity
@Table(name = "roles")
@Data
@ToString
public class Roles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Integer id;
	
	@Column(length = 16)
	private String name;
	
	
	@Override
	public String toString() {
		return "Roles [id=" + id + ", name=" + name + "]";
	}
	public Roles() {
		
	}
	public Roles(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
