package pt.criticalsoftware.platform.style;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IStyleBusinessService;

@Named
@SessionScoped
public class StyleBean implements Serializable{

	private static final long serialVersionUID = -6536114841871506611L;

	private final Logger logger = LoggerFactory.getLogger(StyleBean.class);

	@Inject
	TextBean ttBean;

	@EJB
	IStyleBusinessService business;

	private List<String> texts;
	private List<String> textsComplete;
	private String selectedTT;
	private String theme;
	private Map<String, String> themeMap;
	private String selectedLogo, logoName;



	public StyleBean() {
		themeMap = new LinkedHashMap<String, String>();
		themeMap.put("Default", "default.css");
		themeMap.put("Blue", "blue.css");
		themeMap.put("Red", "red.css");
		texts=new ArrayList <String>();
		textsComplete=new ArrayList <String>();
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
		if(theme==null)
			return themeMap.get("Default");
		else
			return themeMap.get(getTheme());
	}


	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getLogoName() {
		if (selectedLogo==null)
			logoName="logocritical.png";
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getSelectedLogo() {

		return selectedLogo;
	}

	public void setSelectedLogo(String selectedLogo) {
		this.selectedLogo = selectedLogo;
	}
	public void onChange(){
		if(this.selectedLogo.equals("logocritical"))
			this.logoName="logocritical.png";
		else
			this.logoName="logocriticalbw.png";
	}
	
	public void persistThemes(){
		texts= ttBean.getTexts();
		textsComplete=ttBean.getTextsComplete();
		//		selectedTT=ttBean.getSelectedText();

		selectedTT=ttBean.getOptionText();
		//		if(selectedTT==null)
		//			selectedTT="text1";
		//		logger.info("teto seleccionado" +selectedTT );
		//		logger.info("Teema seleccionado"+ this.theme);

		business.saveTheme(this.selectedTT, this.theme, this.logoName);
//		business.saveTheme(this.selectedTT, this.theme);
		FacesContext.getCurrentInstance().addMessage("chooseThemeMsg", new FacesMessage(FacesMessage.SEVERITY_INFO,"Tema alterado com sucesso",null));
	}
}

