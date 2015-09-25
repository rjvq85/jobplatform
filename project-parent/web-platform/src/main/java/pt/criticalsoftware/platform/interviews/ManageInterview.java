package pt.criticalsoftware.platform.interviews;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.notifications.IMailSender;
import pt.criticalsoftware.service.persistence.states.InterviewState;

@Named
@RequestScoped
public class ManageInterview {

	private static final Logger logger = LoggerFactory.getLogger(ManageInterview.class);

	@EJB
	private IInterviewBusinessService business;
	@EJB
	private IMailSender mailSender;

	public void deleteInterview(IInterview interv) {
		try {
			if (!interv.getInterviewState().equals(InterviewState.DONE)) {
				business.deleteInterview(interv);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrevista cancelada com sucesso", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				throw new Exception("Entrevista já realizada");
			}
		} catch (Exception e) {
			logger.error(e.getStackTrace().toString());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cancelar entrevista",
					e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void updateInterview(IInterview interv) {
		try {
			if (!interv.getInterviewState().equals(InterviewState.DONE)) {
				business.updateInterview(interv);
				String path = getRequest().getScheme() + "://" + getRequest().getServerName() + ":"
						+ getRequest().getServerPort() + getRequest().getContextPath();
				mailSender.sendEmail(interv, interv.getInterviewers(), path);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrevista actualizada com sucesso",
						"");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				throw new Exception("Entrevista já realizada");
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao actualizar entrevista", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

}