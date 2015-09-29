package pt.criticalsoftware.platform.report;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Named
@RequestScoped
public class pdfProposalMail {

	private final Logger logger = LoggerFactory.getLogger(pdfProposalMail.class);
	
	private Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.RED);
	private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,	Font.BOLD);


	public String generatMain(ArrayList<IPosition> positions, boolean file,  boolean file1, String documentNumber){
		if (documentNumber==null){
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100);
			documentNumber="a"+randomInt;
		}

		String FILE = "proposals"+documentNumber+".pdf";
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(FILE));
			document.open();
			addMetaData(document);
			//add logo Critical
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String logo = servletContext.getRealPath("") + File.separator + "resources"
					+ ""   + File.separator + "imgs" + File.separator + "criticalIcon.jpg";
			Image image = Image.getInstance(logo);
			document.add(image);
			
			addTitlePage(document);
			Paragraph preface = new Paragraph();
			addEmptyLine(preface, 4);
			document.add(preface);
			
			// add chart
			if (file){
				Image image2 = Image.getInstance("prop.png");
				document.add(image2);
			}
			if (file1){
				Image image3 = Image.getInstance("prop1.png");
				document.add(image3);
			}
			Paragraph preface2 = new Paragraph();
			addEmptyLine(preface2, 2);
			document.add(preface2);

			PdfPTable table = new PdfPTable(5); // 5 columns.

			PdfPCell cell1 = new PdfPCell(new Paragraph("Data abertura"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Empresa"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Area Tecnica"));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Localizacao"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Numero de vagas"));

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell("1");
			table.addCell("2");
			table.addCell("3");
			table.addCell("4");
			table.addCell("5");
			for (IPosition interv:positions){
				table.addCell(interv.getOpenDate().toString());
				table.addCell(interv.getCompany() );
				table.addCell(interv.getTechnicalArea().getName());
				table.addCell(interv.getVacancies().toString());
			}
			document.add(table);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FILE;
	}
	private void addMetaData(Document document) {
		document.addTitle("Relatorio Propostas");
		document.addKeywords("Propostas");
		document.addAuthor("Utilizador");

	}
	private void addTitlePage(Document document)
			throws DocumentException {
		
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Relatório De Propostas Apresentadas Por Período De Tempo", catFont));
		addEmptyLine(preface, 3);
		preface.add(new Paragraph("Data de criação " + new Date().toString(),smallBold));
		preface.add(new Paragraph("Este documento é uma versão em pdf do original gerado na aplicação ", smallBold));
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Serve o documento para ser enviado por mail, para uso externo da rede", redFont));
		document.add(preface);
		Paragraph preface2 = new Paragraph();
		document.add(preface2);
		addEmptyLine(preface2, 4);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
