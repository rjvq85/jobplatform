package pt.criticalsoftware.platform.report;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.model.IInterview;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import java.io.Serializable;

@Named
@RequestScoped
public class pdfInterviewsMail implements Serializable {

	private static final long serialVersionUID = -5593808828125931619L;
	private final Logger logger = LoggerFactory.getLogger(pdfInterviewsMail.class);
	
	private Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.RED);
	private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,	Font.BOLD);


	public String generatMain(ArrayList<IInterview> interviews, boolean file, String documentNumber){
		if (documentNumber==null){
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100);
			documentNumber="a"+randomInt;
		}

		String FILE = "interview"+documentNumber+".pdf";
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
				Image image2 = Image.getInstance("out.png");
				document.add(image2);
			}

			Paragraph preface2 = new Paragraph();
			addEmptyLine(preface2, 2);
			document.add(preface2);

			PdfPTable table = new PdfPTable(4); // 4 columns.

			PdfPCell cell1 = new PdfPCell(new Paragraph("Data da Entrevista"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Area da posicao"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Nome do candidato"));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Resultado da Entrevista"));

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell("1");
			table.addCell("2");
			table.addCell("3");
			table.addCell("4");
			for (IInterview interv:interviews){
				table.addCell(interv.getDate().toString());
				table.addCell(interv.getCandidacy().getCandidate().getFirstName() + " "+interv.getCandidacy().getCandidate().getLastName() );
				table.addCell(interv.getPosition().getTechnicalArea().getName());
				table.addCell(interv.getGlobalRatingString());
			}
			document.add(table);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FILE;
	}
	private void addMetaData(Document document) {
		document.addTitle("Relatorio Entrevistas");
		document.addKeywords("Entrevistas");
		document.addAuthor("Utilizador");

	}
	private void addTitlePage(Document document)
			throws DocumentException {
		logger.info("Entrou no add title");
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Relatório de entrevistas por período de tempo", catFont));
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
