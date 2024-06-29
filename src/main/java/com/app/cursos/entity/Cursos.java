package com.app.cursos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="cursos")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Cursos {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(length=128, nullable=false)
	private String titulo;
	@Column(length=256)
	private String descripcion;
	@Column(nullable=false)
	private int nivel;	
	@Column(name="estado_publicacion")
	private boolean Publicado;
	
	public Cursos() {
		
	}
		
	public Cursos(Integer id, String titulo, String descripcion, int nivel, boolean Publicado) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.Publicado = Publicado;
	}
	
	
	
	@Override
	public String toString() {
		return "Cursos [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", nivel=" + nivel
				+ ", Publicado=" + Publicado + "]";
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public boolean getPublicado() {
		return Publicado;
	}
	public void setPublicado(boolean Publicado) {
		this.Publicado = Publicado;
	}

	

}

