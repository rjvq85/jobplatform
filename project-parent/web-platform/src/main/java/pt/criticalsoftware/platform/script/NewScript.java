package pt.criticalsoftware.platform.script;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IQuestionBusinessService;
import pt.criticalsoftware.service.business.IScriptBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateTitleException;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.persistence.questions.AnswerType;
import pt.criticalsoftware.service.persistence.questions.Question;

import java.io.Serializable;

@Named
@SessionScoped
public class NewScript implements Serializable {

	private static final long serialVersionUID = 2577919018013073047L;

	@EJB
	private IScriptBusinessService scriptService;
	
	@EJB
	private IQuestionBusinessService questionService;

	private final Logger logger = LoggerFactory.getLogger(NewScript.class);


	private String title;
	private String questionString;
	private String answerStr;
	private AnswerType answer;
	private IQuestion question;
	private List <IQuestion> questions;
	private List<Integer> questionsIds;


	public NewScript() {
		questions=new ArrayList<IQuestion>();
		questionsIds= new ArrayList<Integer>();
		
	}

	private void cleanData(){
		this.title="";
		this.questionString=" ";
		this.answerStr=" ";
		this.questions=new ArrayList<IQuestion>();
		this.questionsIds= new ArrayList<Integer>();
	}

	public String getQuestionString() {
		return questionString;
	}

	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}

	public String getAnswerStr() {
		return answerStr;
	}

	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
		if (answerStr.equalsIgnoreCase("V/F"))
			this.answer=AnswerType.VERDADEIRO_FALSO;
		else if (answerStr.equalsIgnoreCase("1/5"))
			this.answer=AnswerType.UM_A_CINCO;
		else if (answerStr.equalsIgnoreCase("Resposta Livre"))
			this.answer=AnswerType.TEXTO_LIVRE;
	}

	public String getQuestion() {
		return questionString;
	}

	public void setQuestion(String questionString) {
		this.questionString = questionString;
	}

	public AnswerType getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerType answer) {
		this.answer = answer;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		
		this.title = title;
	}

	public List <IQuestion> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List <IQuestion> questions) {
		this.questions = questions;
	}

	public void createQuestion(){
		question=questionService.createQuestion(this.questionString, this.answer);
		this.questions.add(question);
		Integer aux= question.getId();
		this.questionsIds.add(aux);
		this.questionString=" ";
		this.answerStr=" ";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"A questão foi criada com sucesso"));
		
	}

	public String createScript() {
		try {
			
			scriptService.createScript(this.title,this.questionsIds);
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage(
					"O guião " + this.title + " " +"foi criado com sucesso"));
			cleanData();
			return "scriptMain.xhtml?faces-redirect=true";

		} catch (DuplicateTitleException dte) {
			logger.error("Tentativa de registo com um título ("+this.title+") já existente");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Título já existente.", "");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, message);
			this.title="";
			return "newScript.xhtml?faces-redirect=true";
			
		}
		
	
	}


	public void onSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
	}

	public void onUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
	}

	public void onReorder() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	} 
	
	public void onRowReorder(ReorderEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lista reordenada"));
	}
}
