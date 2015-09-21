package pt.criticalsoftware.platform.style;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Named("styleBean")
@SessionScoped
public class StyleBean implements Serializable {

	private static final long serialVersionUID = -6536114841871506611L;

	private String theme;

	private Map<String, String> themeMap;

	public StyleBean() {
		
		themeMap = new LinkedHashMap<String, String>();
		themeMap.put("Blue", "blue.css");
		themeMap.put("Green", "green.css");
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
		return themeMap.get(getTheme());
	}


	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	
}

