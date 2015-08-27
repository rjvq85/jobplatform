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

	@Override
	public Integer getId() {
		return script.getId();
	}

	@Override
	public String getTitle() {
		return script.getTitle();
	}

	@Override
	public void setTitle(String title) {
		script.setTitle(title);
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
