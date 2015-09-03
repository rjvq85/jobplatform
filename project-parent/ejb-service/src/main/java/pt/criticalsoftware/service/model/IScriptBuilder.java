package pt.criticalsoftware.service.model;

import java.util.Collection;
import java.util.List;

import pt.criticalsoftware.service.persistence.questions.Question;

public interface IScriptBuilder {
	
	IScriptBuilder title(String title);
	
	IScriptBuilder reference(String reference);
	
	IScriptBuilder questions(List<IQuestion> questions);
	
	IScript build();

	

}
