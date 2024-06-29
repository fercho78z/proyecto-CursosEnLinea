package com.app.cursos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.cursos.entity.Cursos;

public interface CursosRepository extends JpaRepository<Cursos, Integer> {

	  Page<Cursos> findByTitulo(String keyword,Pageable pageable);
}
