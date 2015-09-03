package pt.criticalsoftware.platform.script;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IScriptBusinessService;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IScript;

@Named
@RequestScoped
public class ScriptListView implements Serializable{

	private static final long serialVersionUID = 9182858810402417073L;
	private final Logger logger = LoggerFactory.getLogger(ScriptListView.class);

	@EJB
	private IScriptBusinessService scriptService;

	private List<IScript> scripts;
	private String searchCode;
	private String scriptWord;
	private boolean searchBoolean=false;

	public ScriptListView() {
	}

	public List<IScript> showScripts() {
		return scriptService.getAllScripts();
	}
	public void search(){
		if (scriptWord.equalsIgnoreCase("Título"))
			this.scripts= scriptService.getScriptsByTitle(searchCode);
		else if(scriptWord.equalsIgnoreCase("Referência"))
			this.scripts= scriptService.getScriptsByReference(searchCode);
		this.searchBoolean=true;
	}

	public String getScriptWord() {
		return scriptWord;
	}

	public void setScriptWord(String scriptWord) {
		this.scriptWord = scriptWord;
	}
	public void searchAll(){
		this.scripts=getScripts();
	}

	public List<IScript> getScripts() {
		if (!this.searchBoolean)
			this.scripts=showScripts();
		return this.scripts;
	}

	public void setScripts(List<IScript> scripts) {
		this.scripts = scripts;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}






}
