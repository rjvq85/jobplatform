package pt.criticalsoftware.service.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IStyleBusinessService {

//	public void saveTheme(List<String> texts, List<String> textsComplete, String selectedTT, String selectedTheme);

	public void saveTheme(String selectedTT, String selectedTheme, String logoName);
	
//	public void saveTheme(String selectedTT, String selectedTheme);

	public String getSelectedText();
	
	public String getSelectedTheme();
	
	public String getSelecteLogo();
	
	public List<String> getTexts();
	
	public List<String> getTextsComplete();
}
