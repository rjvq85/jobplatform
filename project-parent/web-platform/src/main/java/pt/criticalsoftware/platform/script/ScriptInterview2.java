

package pt.criticalsoftware.platform.script;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.scriptxml.ScriptListXML;
import pt.criticalsoftware.service.scriptxml.ScriptXML;
import pt.criticalsoftware.service.scriptxml.XMLParser;

import java.io.Serializable;

@Named
@SessionScoped
public class ScriptInterview2 implements Serializable {

	private static final long serialVersionUID = 4458453381773998347L;

	private final Logger logger = LoggerFactory.getLogger(ScriptInterview2.class);

	@Inject 
	DynaFormController dyna;
	@EJB
	private IInterviewBusinessService intervBness;

	private String analisysResult=" ";
	private List<IQuestion> questions;

	private List<AnswerScript> answers;

	public ScriptInterview2() {
		answers=new ArrayList<AnswerScript>();
	}

	public void save(){
		IInterview interview = dyna.getInterview();
		XMLParser xmlParser = new XMLParser();
		String intervFeedback = interview.getFeedback();
		ScriptListXML scriptFeedbacks = (null != intervFeedback) ? xmlParser.asXML(intervFeedback) : new ScriptListXML();
		answers=dyna.getAnswers();
		questions=dyna.getQuestions();
		String title=dyna.getTitle();
		int i, size=questions.size();
		LocalDate date = LocalDate.now();

		this.analisysResult+=title;
		this.analisysResult+="\n Data da entrevista" + date.toString() +"\n";
		ScriptXML script = new ScriptXML();
	
		for (i=0; i<size; i++){
			this.analisysResult+=questions.get(i).getQuestionStr()+"\n" ;
			this.analisysResult+=answers.get(i).getAnswer()+"\n";
			script.getQuestionsAndAnswers().put(questions.get(i).getQuestionStr(), answers.get(i).getAnswer());
		}
		String finalComment = answers.get(i).getAnswer();
		logger.info("Resultado da entrevista" + this.analisysResult);
		script.setFinalComment(finalComment);
		Integer interviewerID = (Integer) getSession().getAttribute("userID");
		scriptFeedbacks.getScripts().put(interviewerID, script);
		String feedbackToString = xmlParser.asString(scriptFeedbacks);
		interview.setFeedback(feedbackToString);
		intervBness.updateInterview(interview);
	}
	
	private HttpSession getSession() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return req.getSession();
	}
}


