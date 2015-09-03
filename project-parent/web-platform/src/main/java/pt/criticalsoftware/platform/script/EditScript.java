package pt.criticalsoftware.platform.script;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IQuestionBusinessService;
import pt.criticalsoftware.service.business.IScriptBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateTitleException;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.questions.AnswerType;
import pt.criticalsoftware.service.persistence.questions.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class EditScript implements Serializable {

	private static final long serialVersionUID = 2019521611493509985L;

	private final Logger logger = LoggerFactory.getLogger(EditScript.class);
	@EJB
	private IScriptBusinessService scriptService;
	@EJB
	private IQuestionBusinessService questionService;

	private IScript editScript;
	private IQuestion editQuestion;
	private String title;
	private String answerStr;
	private AnswerType answer;
	private IQuestion question;
	private List <IQuestion> questions;
	private String questionStr;
	private List<Integer> questionsIds;
	private int id;
	private String editQuestionStr, qS, qA;
	private String editQuestionType;
	private List<String> questionTypes;
	private boolean select=false;

	public EditScript() {
		questions=new ArrayList <IQuestion>();
		questionsIds= new ArrayList<Integer>();
		questionTypes= new ArrayList<String>();
		this.questionTypes.add("Resposta Livre");
		this.questionTypes.add("1/5");
		this.questionTypes.add("V/F");
	}
	public void init(){
		this.questions=questionService.getAllQuestionsByScript(this.editScript);
		this.title=this.editScript.getTitle();
	}

	//Questions List--------------
	public List<IQuestion> getQuestions() {
		return this.questions;
	}
	public void setQuestions(List<IQuestion> questions) {
		this.questions = questions;
	}
	//End Questions List--------------
	public void setQuestion(IQuestion question) {
		this.question = question;
	}
	public IQuestion getQuestion() {
		return question;
	}

	public IScript getEditScript() {
		logger.info("O guiao seleccionado é+ ");
		return editScript;
	}
	public void setEditScript(IScript editScript) {
		this.editScript = editScript;
	}

	public String getTitle() {
		logger.info("titulo" + this.editScript.getTitle());
		return this.editScript.getTitle();
	}
	public void setTitle(String title) {
		this.title = title;
	}
	//------------------------------UPDATE SCRIPT-------------------------
	public String updateScript(){

		List<Integer> qId= new ArrayList<Integer>();
		for(IQuestion q:this.questions)
			qId.add(q.getId());
		if (scriptService.verifyEditTitle(title, this.editScript.getId())){
			scriptService.update(this.editScript.getId(),this.title, qId);
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage(
					"O guião " + this.title + " " +"foi editado com sucesso"));
			return "scriptMain.xhtml?faces-redirect=true";
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Título já existente.", "");
			context.addMessage(null, message);
			this.title="";
			return "scriptEditC.xhtml?faces-redirect=true";
		}

	}
	//--------------------------------Create NEW Question
	public void createQuestion(){
		question=questionService.createNewQuestion(this.questionStr, this.answer, this.editScript);
		this.questions.add(question);
		Integer aux= question.getId();
		this.questionsIds.add(aux);
		init();
		this.questionStr=" ";
		this.answerStr=" ";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"A questão foi criada com sucesso"));
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

	public String getQuestionStr() {
		return questionStr;
	}
	public void setQuestionStr(String questionStr) {
		this.questionStr = questionStr;
	}
	//---------------------------Delete Script
	public void deleteScript(){
		List<Integer> qId= new ArrayList<Integer>();
		for(IQuestion q:this.questions)
			qId.add(q.getId());
		scriptService.deleteScript(this.editScript, qId);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"O guião foi eliminado com sucesso"));
	}
	//Question selected-------------------------
	public IQuestion getEditQuestion() {
		return this.editQuestion;
	}
	public void setEditQuestion(IQuestion editQuestion) {
		this.editQuestion = editQuestion;
	}
	//---------------------------Delete Question
	public void deleteQuestion(){
		for(IQuestion q:questions)
			if (q.getId()==this.id){
				questionService.delete(q);
				init();
			}
		for(Integer i:questionsIds)
			if(i==this.id)
				questionsIds.remove(i);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"A questão foi eliminada com sucesso"));
	}
	//---------------------------Update Question
	public void saveQuestion(){
		if (this.editQuestionType.equalsIgnoreCase("V/F"))
			this.answer=AnswerType.VERDADEIRO_FALSO;
		else if (this.editQuestionType.equalsIgnoreCase("1/5"))
			this.answer=AnswerType.UM_A_CINCO;
		else if (this.editQuestionType.equalsIgnoreCase("Resposta Livre"))
			this.answer=AnswerType.TEXTO_LIVRE;

		for(IQuestion q:this.questions)
			if (q.getId()==this.id){
				q.setQuestionStr(this.editQuestionStr);
				q.setAnswerType(this.answer);
				questionService.update(q, id);
			}
		setQuestions(this.questions);
	}

	private void updateSelectedQuestion(IQuestion question){
		this.editQuestion=question;
		this.id=this.editQuestion.getId();	
		this.select=true;
		this.qS=this.editQuestion.getQuestionStr();
		this.qA=this.editQuestion.getAnswer().getName();

	}
	public List<String> getQuestionTypes() {
		if (this.qA!=null){
			logger.info("alterada e a resposta é " + this.qA);
			if (this.qA.equals("verdadeiro/falso")){
				this.questionTypes=new ArrayList<String>();
				this.questionTypes.add("V/F");
				this.questionTypes.add("1/5");
				this.questionTypes.add("Resposta Livre");
			}else if (this.qA.equals("1 a 5")){
				this.questionTypes=new ArrayList<String>();
				this.questionTypes.add("1/5");
				this.questionTypes.add("V/F");
				this.questionTypes.add("Resposta Livre");
			}else if (this.qA.equals("texto livre")){
				this.questionTypes=new ArrayList<String>();
				this.questionTypes.add("Resposta Livre");
				this.questionTypes.add("1/5");
				this.questionTypes.add("V/F");
			}
		}
		return questionTypes;
	}
	public void setQuestionTypes(List<String> questionTypes) {
		this.questionTypes = questionTypes;
	}

	public String getEditQuestionType() {
		if (!this.select)
			return this.editQuestionType;
		else
			return this.qA;
	}
	public void setEditQuestionType(String editQuestionType) {
		this.editQuestionType = editQuestionType;
		this.qA= editQuestionType;
	}

	public String getEditQuestionStr() {
		if (!this.select)
			return this.editQuestionStr;
		else
			return this.qS;
	}
	public void setEditQuestionStr(String editQuestionStr) {
		this.editQuestionStr = editQuestionStr;
		this.qS= editQuestionStr;
	}

	//-----------------------------------EVENTS
	public void onRowSelect(SelectEvent event) {
		this.editScript=((IScript) event.getObject());
		init();
	}
	public void onRowUnselect(UnselectEvent event) {
		this.editScript=((IScript) event.getObject());
		this.editScript=null;
	}

	public void onSelect(SelectEvent event) {
		this.editQuestion=((IQuestion) event.getObject());
		updateSelectedQuestion(this.editQuestion);
	}
	public void onRowReorder(ReorderEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"Guião reordenado"));
	}
}
