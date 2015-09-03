

package pt.criticalsoftware.platform.script;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.model.IQuestion;

import java.io.Serializable;

@Named
@SessionScoped
public class ScriptInterview implements Serializable {

	private static final long serialVersionUID = 4458453381773998347L;

	private final Logger logger = LoggerFactory.getLogger(ScriptInterview.class);

	@Inject 
	DynaFormController dyna;

	private String analisysResult=" ";
	private List<IQuestion> questions;

	private List<AnswerScript> answers;

	public ScriptInterview() {
		answers=new ArrayList<AnswerScript>();
	}

	public void save(){
		answers=dyna.getAnswers();
		questions=dyna.getQuestions();
		String title=dyna.getTitle();
		int i, size=questions.size();
		LocalDate date = LocalDate.now();

		this.analisysResult+=title;
		this.analisysResult+="\n Data da entrevista" + date.toString() +"\n";
	
		for (i=0; i<size; i++){
			this.analisysResult+=questions.get(i).getQuestionStr()+"\n" ;
			this.analisysResult+=answers.get(i).getAnswer()+"\n";
		}
		this.analisysResult+="Análise final " + "\n" +answers.get(i).getAnswer()+"\n";
		logger.info("Resultado da entrevista" + this.analisysResult);
//		guardar no respectivo candidato
		

	}
}


