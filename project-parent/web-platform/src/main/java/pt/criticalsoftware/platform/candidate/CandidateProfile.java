package pt.criticalsoftware.platform.candidate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.model.ICandidate;

@Named
@ViewScoped
public class CandidateProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CandidateProfile.class);

	@EJB
	private ICandidateBusinessService business;

	private Integer candidateId;
	private ICandidate candidate;
	private StreamedContent file;

	public void searchCandidate() {
		if (null != candidateId) {
			try {
				candidate = business.getCandidateById(candidateId);
			} catch (Exception e) {
				logger.error("Tentativa de pesquisa de perfil do candidato com id:" + candidateId
						+ ", mas nenhuma correspondência foi encontrada.");
			}
		}
	}

	public void generateFile() {
		candidate = business.getCandidateById(candidateId);
		String cv = candidate.getCv();
		String contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(cv);
		try {
			file = new DefaultStreamedContent(new FileInputStream(cv), contentType, candidate.getFirstName() + "_" + candidate.getLastName() + "_CV.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public ICandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(ICandidate candidate) {
		this.candidate = candidate;
	}

	public StreamedContent getFile() {
		generateFile();
		return file;
	}

}
