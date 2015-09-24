package pt.criticalsoftware.service.persistence;

import java.util.Collection;
import java.util.List;


public interface IStylePersistenceService {

//	public void saveTheme(List<String> texts, List<String> textsComplete, String selectedTT, String selectedTheme);

	public void saveTheme(String selectedTT, String selectedTheme, String logoName);
	public void saveTheme(String selectedTT, String selectedTheme);

	public String getSelectedText();

	public String getSelectedTheme();

	public List<String> getTexts();

	public List<String> getTextsComplete();
	
	public String getSelectedLogo();

}
