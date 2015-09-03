package pt.criticalsoftware.domain.proxies;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IScriptBuilder;
@RequestScoped
public class ScriptBuilder implements IScriptBuilder {
	
	private ScriptProxy script;
		
	public ScriptBuilder() {
		script = new ScriptProxy();
	}
	
	
	@Override
	public IScript build() {
		return script;
	}


	@Override
	public IScriptBuilder title(String title) {
		script.setTitle(title);
		return this;
	}


	@Override
	public IScriptBuilder reference(String reference) {
		script.setReference(reference);
		return this;
	}


	@Override
	public IScriptBuilder questions(List<IQuestion> questions) {
		script.setQuestions(questions);
		return null;
	}

}
