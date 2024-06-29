package com.app.cursos.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.app.cursos.entity.Cursos;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;


public class CursoExportPDF {

	private List<Cursos> lstCursos;

	public CursoExportPDF(List<Cursos> lstCursos) {
		super();
		this.lstCursos = lstCursos;
	}
	private void writeTableHeader(PdfPTable pdfTable) {
		PdfPCell cell=new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		cell.setPhrase(new Phrase("ID",font));
		pdfTable.addCell(cell);
		cell.setPhrase(new Phrase("Titulo",font));
		pdfTable.addCell(cell);
		cell.setPhrase(new Phrase("Descripcion",font));
		pdfTable.addCell(cell);
		cell.setPhrase(new Phrase("Nivel",font));
		pdfTable.addCell(cell);
		cell.setPhrase(new Phrase("Publicado",font));
		pdfTable.addCell(cell);
		
	}
	private void writeTableData(PdfPTable table) {
		for(Cursos curso:lstCursos) {
			table.addCell(String.valueOf(curso.getId()));
			table.addCell(curso.getTitulo());
			table.addCell(curso.getDescripcion());
			table.addCell(String.valueOf(curso.getNivel()));
			table.addCell(String.valueOf(curso.getPublicado()));	
		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		Paragraph p = new Paragraph("Lista de Cursos",font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		PdfPTable table= new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new float[] {1.3f,3.5f,2.0f,1.5f,1.5f});
		table.setSpacingBefore(10);
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();
	}
	
}
	