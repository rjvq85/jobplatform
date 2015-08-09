package pt.criticalsoftware.domain.proxies;

import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IScriptBuilder;

public class ScriptBuilder implements IScriptBuilder {
	
	private ScriptProxy script;
	
	public ScriptBuilder() {
		script = new ScriptProxy();
	}
	
	
	@Override
	public IScript build() {
		return script;
	}

}
