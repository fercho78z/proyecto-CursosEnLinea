package com.app.cursos.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.cursos.entity.Cursos;
import com.app.cursos.repository.CursosRepository;


@Service
public class CursoServicesImp implements CursoServices{
	
	@Autowired
	private CursosRepository cursoR;
	@Override
	public List<Cursos> ListarTodosLosCursos() {
		// TODO Auto-generated method stub
		return cursoR.findAll();
	}

	@Override
	public Cursos obtenerPorId(Integer Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursos crearCurso(Cursos curso) {
		return cursoR.save(curso);
	}

	@Override
	public Cursos actCurso(Integer Id, Cursos curso) {
		Cursos cursoBD=cursoR.findById(Id).orElse(null);
		if(cursoBD !=null) {
			cursoBD.setNivel(curso.getNivel());
			cursoBD.setTitulo(curso.getTitulo());
			cursoBD.setDescripcion(curso.getDescripcion());
			cursoBD.setPublicado(curso.getPublicado());
			return cursoR.save(cursoBD);
		}
		return null;
	}

	@Override
	public void eliminarCurso(Integer Id) {
		cursoR.deleteById(Id);
	}

	@Override
	public long contarCurso() {
		// TODO Auto-generated method stub
		return 0;
	}

}
