package com.app.cursos.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.cursos.entity.Cursos;
import com.app.cursos.reports.CursoExportExcel;
import com.app.cursos.reports.CursoExportPDF;
import com.app.cursos.repository.CursosRepository;
import com.app.cursos.services.CursoServices;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

@Controller
public class CursosController {
	@Autowired
	private CursoServices cursosS;
	@Autowired
	private CursosRepository cursosR;

	@GetMapping("/cursos")
	public String listarCursos(Model modelo,@PageableDefault(size=5,page=0) Pageable pageable) {
		/*try {
			List<Cursos> cursos = new ArrayList<>();
			Pageable paging = PageRequest.of(page - 1, size);
			Page<Cursos> pageCursos = null;
			if (keyword == null) {
				pageCursos = cursosR.findAll(paging);
				
			} else {
				pageCursos = cursosR.findByTitulo(keyword, paging);
				modelo.addAttribute("keyword", keyword);
			}
			cursos = pageCursos.getContent();
			modelo.addAttribute("cursos", cursos);
			modelo.addAttribute("currentPage", pageCursos.getNumber() + 1);
			modelo.addAttribute("totalItems", pageCursos.getTotalElements());
			modelo.addAttribute("totalPages", pageCursos.getTotalPages());
			modelo.addAttribute("pageSize", size);
		} catch (Exception exception) {
			modelo.addAttribute("Message Error:", exception.getMessage());
		}*/
		List<Cursos> cursos = new ArrayList<>();
		Page<Cursos> pageCursos = cursosR.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
		modelo.addAttribute("page",pageCursos);
		var currentPage= pageCursos.getNumber();
		var totalPages=pageCursos.getTotalPages();
		var start= Math.max(1, currentPage);
		var end = Math.min(currentPage+5, totalPages);
		cursos = pageCursos.getContent();
		modelo.addAttribute("cursos", cursos);
		
		if(totalPages>0) {
			
			List<Integer> pageNumbers=new ArrayList<>();
			for(int i=start;i<=end; i++) {
				pageNumbers.add(i);
			}
			modelo.addAttribute("pageNumber",pageNumbers);
		}
		
		List<Integer> pageSizeOption=Arrays.asList(5,10,50,100);
		modelo.addAttribute("pageSizeOption",pageSizeOption);

		
		
		
		
		return "cursos";
	}

	/*
	 * //Listar cursos sin paginacion
	 * 
	 * @GetMapping("/cursos") public String listarCursos(Model modelo) {
	 * modelo.addAttribute("cursos", cursosS.ListarTodosLosCursos()); 
	 * return "cursos"; }
	 */

	@GetMapping("/cursos/nuevo")
	public String agregarCursos(Model modelo) {
		Cursos curso = new Cursos();
		curso.setPublicado(true);
		modelo.addAttribute("cursos", new Cursos());
		modelo.addAttribute("accion", "/cursos/save");
		modelo.addAttribute("pageTitle", "Nuevo Curso");
		return "cursos-form";
	}

	@PostMapping("/cursos/save")
	public String guardarCurso(@ModelAttribute Cursos curso, RedirectAttributes redirectAttributes) {

		cursosS.crearCurso(curso);
		redirectAttributes.addFlashAttribute("message", "Se guardo con exito");
		return "redirect:/cursos";
	}

	@GetMapping("/cursos/editar/{id}")
	public String mostrarFormularioEditarCursos(@PathVariable Integer id, @ModelAttribute Cursos curso, Model modelo) {

		Cursos cursos2 = cursosR.findById(id).get();
		modelo.addAttribute("cursos", cursos2);
		modelo.addAttribute("pageTitle", "Actualizar Curso");
		modelo.addAttribute("accion", "/cursos/editar/" + id);

		return "cursos-form";
	}

	@PostMapping("/cursos/editar/{id}")
	public String formularioEditarCursos(@PathVariable Integer id, @ModelAttribute Cursos curso) {
		cursosS.actCurso(id, curso);
		return "redirect:/cursos";
	}

	@GetMapping("/cursos/eliminar/{id}")
	public String eliminarFormularioCurso(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		cursosS.eliminarCurso(id);
		redirectAttributes.addFlashAttribute("message", "Se elimino con exito");
		return "redirect:/cursos";
	}

	@GetMapping("/export/pdf")
	public void generarReportePDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateformat.format(new Date());

		String headerKey = "Content-Disposition";
		String headerVaalue = "attachment; filename=cursos" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerVaalue);
		List<Cursos> cursos = cursosR.findAll();
		CursoExportPDF exporterPDF = new CursoExportPDF(cursos);
		exporterPDF.export(response);
	}

	@GetMapping("/export/excel")
	public void generarReporteExcel(HttpServletResponse response) throws DocumentException, IOException {

		response.setContentType("application/octet-stream");
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateformat.format(new Date());

		String headerKey = "Content-Disposition";
		String headerVaalue = "attachment; filename=cursos" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerVaalue);
		List<Cursos> cursos = cursosR.findAll();
		CursoExportExcel exporterExcel = new CursoExportExcel(cursos);
		exporterExcel.export(response);
	}

}
