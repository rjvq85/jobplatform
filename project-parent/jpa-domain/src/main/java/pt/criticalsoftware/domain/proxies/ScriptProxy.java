package pt.criticalsoftware.domain.proxies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.domain.entities.InterviewEntity;
import pt.criticalsoftware.domain.entities.QuestionEntity;
import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;


public class ScriptProxy implements IEntityAware<ScriptEntity>,IScript {
	
	private ScriptEntity script;
	private final Logger logger = LoggerFactory.getLogger(ScriptProxy.class);
	


	public ScriptProxy() {
		this(null);
	}

	public ScriptProxy(ScriptEntity entity) {
		script = entity != null ? entity : new ScriptEntity();
	}

	@Override
	public ScriptEntity getEntity() {
		return script;
	}

	@Override
	public Integer getId() {
		return script.getId();
	}

	

	@Override

	public String getTitle() {
		return script.getTitle();
	}

	@Override

	public void setReference(String reference) {
		script.setReference(reference);
		
	}

	@Override
	public String getReference() {
		
		return script.getReference();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setQuestions(Collection<IQuestion> questions) {
		List<QuestionEntity> questionEnt = new ArrayList<>();
		for (IQuestion question : questions) {
			if (question instanceof IEntityAware<?>) {
				questionEnt.add(((IEntityAware<QuestionEntity>) question).getEntity());
			}
		}
			
	}

	@Override
	public Collection<IQuestion> getQuestions() {
		List<IQuestion> questions = new ArrayList<>();
		for (QuestionEntity question : script.getQuestions()) {
			questions.add(new QuestionProxy(question));
		}
		return questions;
	}
	public void setTitle(String title) {
		script.setTitle(title);
	}
	
	@Override
	public List<IInterview> getInterviews() {
		List<IInterview> interviews = new ArrayList<>();
		script.getInterviews().stream().forEach(entity -> interviews.add(new InterviewProxy(entity)));
		return interviews;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setInterviews(List<IInterview> interviews) {
		List<InterviewEntity> entities = new ArrayList<>();
		for(IInterview interview:interviews) {
			if (interview instanceof IEntityAware<?>) {
				entities.add(((IEntityAware<InterviewEntity>) interview).getEntity());
			}
		}
		script.setInterviews(entities);
	}

	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && script.getId() != null)
				? script.getId().equals(((ScriptProxy) other).getId()) : (other == this);
	}

	@Override
	public int hashCode() {
		return (script.getId() != null) ? (getClass().hashCode() + script.getId().hashCode()) : super.hashCode();
	}
	
	@Override
	public String toString() {
		return String.valueOf(script.getId());

	}

}
