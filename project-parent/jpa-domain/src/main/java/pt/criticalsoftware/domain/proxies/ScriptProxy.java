package pt.criticalsoftware.domain.proxies;

import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.service.model.IScript;

public class ScriptProxy implements IEntityAware<ScriptEntity>, IScript {
	
	private ScriptEntity script;
	
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

}
