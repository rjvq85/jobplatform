package pt.criticalsoftware.platform.candidate;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.model.ICandidate;

@Named
@RequestScoped
public class CandidateProfile {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateProfile.class);
	
	@EJB
	private ICandidateBusinessService business;
	
	private Integer candidateId;
	private ICandidate candidate;
	
	public void searchCandidate() {
		if (null != candidateId) {
			try {
				candidate = business.getCandidateById(candidateId);
			} catch (Exception e) {
				logger.error("Tentativa de pesquisa de perfil do candidato com id:" + candidateId + ", mas nenhuma correspondência foi encontrada.");
			}
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

}
