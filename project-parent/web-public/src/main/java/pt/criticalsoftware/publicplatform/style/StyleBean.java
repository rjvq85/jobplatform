package pt.criticalsoftware.publicplatform.style;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IStyleBusinessService;

@Named("styleBean")
@SessionScoped
public class StyleBean implements Serializable {

	private static final long serialVersionUID = -6536114841871506611L;

	@EJB
	IStyleBusinessService business;
	
	private String theme;

	private Map<String, String> themeMap;

	public StyleBean() {
		
		themeMap = new LinkedHashMap<String, String>();
		themeMap.put("Default", "jobs.css");
		themeMap.put("Blue", "blue.css");
		themeMap.put("Red", "red.css");
	}
	public Map<String, String> getThemeMap() {
		return themeMap;
	}


	public void setThemeMap(Map<String, String> themeMap) {
		this.themeMap = themeMap;
	}

	@Produces
	@Named("themes")
	@ApplicationScoped
	public List<String> getThemes() {
		return new ArrayList<String>(themeMap.keySet());
	}


	public String getThemeCss() {
		if(business.getSelectedTheme()==null)
			return themeMap.get("Default");
		else
			return themeMap.get(business.getSelectedTheme());
	
	}


	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
