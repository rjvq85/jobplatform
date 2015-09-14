package pt.criticalsoftware.service.scriptxml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScriptListXML {
	
	@XmlElement(name = "guioes")
	private Map<Integer, ScriptXML> scripts;
	
	public ScriptListXML() {
		scripts = new HashMap<>();
	}

	public Map<Integer, ScriptXML> getScripts() {
		return scripts;
	}

	public void setScripts(Map<Integer, ScriptXML> scripts) {
		this.scripts = scripts;
	}
	
	
	
}
