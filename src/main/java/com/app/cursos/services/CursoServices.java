package com.app.cursos.services;

import java.util.List;

import com.app.cursos.entity.Cursos;

public interface CursoServices {
	List<Cursos> ListarTodosLosCursos();

	Cursos obtenerPorId(Integer Id);

	Cursos crearCurso(Cursos curso);

	Cursos actCurso(Integer Id, Cursos curso);

	void eliminarCurso(Integer Id);

	long contarCurso();

}
