package pt.criticalsoftware.platform.interviews;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.model.IInterview;

@Named
@RequestScoped
public class ManageInterview {


	private static final Logger logger = LoggerFactory.getLogger(ManageInterview.class);

	@EJB
	private IInterviewBusinessService business;

	public void deleteInterview(IInterview interv) {
		try {
			business.deleteInterview(interv);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Entrevista cancelada com sucesso","");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			logger.error(e.getStackTrace().toString());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cancelar entrevista","");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void updateInterview(IInterview interv) {
		try {
			business.updateInterview(interv);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Entrevista actualizada com sucesso","");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			logger.error(e.getStackTrace().toString());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cancelar entrevista","");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
}