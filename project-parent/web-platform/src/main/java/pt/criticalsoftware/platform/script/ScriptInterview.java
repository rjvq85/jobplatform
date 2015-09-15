
package pt.criticalsoftware.platform.script;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.scriptxml.ScriptListXML;
import pt.criticalsoftware.service.scriptxml.ScriptXML;
import pt.criticalsoftware.service.scriptxml.XMLParser;

import java.io.Serializable;

@Named
@SessionScoped
public class ScriptInterview implements Serializable {

	private static final long serialVersionUID = 4458453381773998347L;

	private final Logger logger = LoggerFactory.getLogger(ScriptInterview.class);

	@Inject
	DynaFormController dyna;
	@EJB
	private IInterviewBusinessService intervBness;

	private List<IQuestion> questions;

	private List<AnswerScript> answers;

	public ScriptInterview() {
		answers = new ArrayList<AnswerScript>();
	}

	public String save() {
		try {
			IInterview interview = dyna.getInterview();

			// Get feedback from Interview: if exists, retrieves it, else
			// creates a
			// new one.
			XMLParser xmlParser = new XMLParser();
			String intervFeedback = interview.getFeedback();
			ScriptListXML scriptFeedbacks = (null != intervFeedback) ? xmlParser.asXML(intervFeedback)
					: new ScriptListXML();
			//

			answers = dyna.getAnswers();
			questions = dyna.getQuestions();
			int i, size = questions.size();

			ScriptXML script = new ScriptXML();

			for (i = 0; i < size; i++) {
				script.getQuestionsAndAnswers().put(questions.get(i).getQuestionStr(), answers.get(i).getAnswer());
			}
			String finalComment = answers.get(i).getAnswer();
			script.setFinalComment(finalComment);
			Integer interviewerID = (Integer) getSession().getAttribute("userID");
			scriptFeedbacks.getScripts().put(interviewerID, script);
			// Transform retrieved feedback (plus the new added) into a String
			// to
			// persist
			String feedbackToString = xmlParser.asString(scriptFeedbacks);
			interview.setFeedback(feedbackToString);
			intervBness.updateInterview(interview);
			logger.debug("Feedback criado para a entrevista com referência: " + interview.getReference());
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Feedback submetido com sucesso!", null);
			context.addMessage(null, msg);
			return "feedbackSuccess";
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao guardar feedback", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	private HttpSession getSession() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return req.getSession();
	}
}
