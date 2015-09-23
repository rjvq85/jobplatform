package pt.criticalsoftware.service.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import pt.criticalsoftware.service.persistence.IStylePersistenceService;

@Stateless
public class StyleBusinessService implements IStyleBusinessService{
	
	private final Logger logger = LoggerFactory.getLogger(StyleBusinessService.class);
	@EJB
	private IStylePersistenceService persist;
	
//	@Override
//	public void saveTheme(List<String> texts, List<String> textsComplete,
//			String selectedTT, String selectedTheme) {
//		persist.saveTheme(texts, textsComplete, selectedTT, selectedTheme);
//		
//	}

	@Override
	public void saveTheme(String selectedTT, String selectedTheme, String logoName ) {
		persist.saveTheme(selectedTT, selectedTheme, logoName);
		
	}
	
	@Override
	public String getSelectedText() {
		logger.info("Entrou no get do business");
		return persist.getSelectedText();
	}

	@Override
	public String getSelectedTheme() {
		return persist.getSelectedTheme();
	}

	@Override
	public List<String> getTexts() {
		return persist.getTexts();
	}

	@Override
	public List<String> getTextsComplete() {
		return persist.getTextsComplete();
	}

	@Override
	public String getSelecteLogo() {
		// TODO Auto-generated method stub
		return persist.getSelectedLogo();
	}

}
