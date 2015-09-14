package pt.criticalsoftware.platform.script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import pt.criticalsoftware.service.business.IQuestionBusinessService;
import pt.criticalsoftware.service.business.IScriptBusinessService;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;

import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class DynaFormController2 implements Serializable {

	private static final long serialVersionUID = 1578202248436017153L;
	private final Logger logger = LoggerFactory.getLogger(DynaFormController2.class);

	private IScript script;
	private IInterview interview;
	private List<IQuestion> questions;

	@EJB
	private IScriptBusinessService scriptService;
	@EJB
	private IQuestionBusinessService questionService;
	private DynaFormModel model;
	private String title;

	private String scriptReference;

	public DynaFormController2() {
	}

	public void startup() {
		scriptReference = interview.getScript().getReference();
		this.script = scriptService.getScriptsByReference(scriptReference).get(0);
		this.questions = questionService.getAllQuestionsByScript(script);
		this.title = this.script.getTitle();
		int listSize = this.questions.size();
		int i;
		String answer = null;
		model = new DynaFormModel();
		int countNumber = 1;
		String input = null;
		String question = null;
		for (i = 0; i <= listSize; i++) {
			if (listSize > i) {
				int j = 0;
				while (countNumber != questions.get(j).getNumber()) {
					j++;
				}
				input = questions.get(j).getAnswer().getName();
				question = questions.get(j).getQuestionStr();
				countNumber++;

				String inputStyle = null;
				if (input.equals("1 a 5"))
					inputStyle = "typeSelectValue";
				if (input.equals("verdadeiro/falso"))
					inputStyle = "typeSelectBoolean";
				if (input.equals("texto livre"))
					inputStyle = "typeInpStr";

				DynaFormRow row = model.createRegularRow();
				DynaFormLabel labelQ = row.addLabel(question);
				row = model.createRegularRow();
				DynaFormControl controlA = row.addControl(new AnswerScript(answer), inputStyle);
				labelQ.setForControl(controlA);
			} else {
				DynaFormRow row = model.createRegularRow();
				DynaFormLabel labelQ = row.addLabel("Análise Final");
				row = model.createRegularRow();
				DynaFormControl controlA = row.addControl(new AnswerScript(answer), "typeInpArea");
				labelQ.setForControl(controlA);
			}

		}

	}

	public List<IQuestion> getQuestions() {
		return questions;
	}

	public String getTitle() {
		return title;
	}

	public List<AnswerScript> getAnswers() {
		if (model == null) {
			return null;
		}

		List<AnswerScript> questions = new ArrayList<AnswerScript>();
		for (DynaFormControl dynaFormControl : model.getControls()) {
			questions.add((AnswerScript) dynaFormControl.getData());
		}

		return questions;
	}

	public DynaFormModel getModel() {
		return model;
	}

	public String getScriptReference() {
		return scriptReference;
	}

	public void setScriptReference(String scriptReference) {
		System.out.println("\n\n__~~** Entrou **~~__\n\n");
		this.scriptReference = scriptReference;
	}

	public IInterview getInterview() {
		return interview;
	}

	public void setInterview(IInterview interview) {
		this.interview = interview;
	}

}
