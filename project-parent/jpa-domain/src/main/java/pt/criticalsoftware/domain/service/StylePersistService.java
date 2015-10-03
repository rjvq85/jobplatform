package pt.criticalsoftware.domain.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.domain.entities.ThemesEntity;
import pt.criticalsoftware.service.persistence.IStylePersistenceService;

@Stateless
public class StylePersistService implements IStylePersistenceService {

	private final Logger logger = LoggerFactory.getLogger(StylePersistService.class);
	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public void saveTheme(String selectedTT, String selectedTheme, String logoName) {

		TypedQuery<ThemesEntity> query = em.createNamedQuery("ThemesEntity.getTheme", ThemesEntity.class);
		List<ThemesEntity> result = query.getResultList();
		if (result != null)
			for (ThemesEntity t : result)
				em.remove(t);

		ThemesEntity themes = new ThemesEntity(selectedTT, selectedTheme, logoName);
		em.persist(themes);
	}

	@Override
	public String getSelectedText() {
		logger.info("Entrou no get do persitence");
		TypedQuery<ThemesEntity> query = em.createNamedQuery("ThemesEntity.getTheme", ThemesEntity.class);

		logger.info("O numero de resultados é " + query.getMaxResults());
		ThemesEntity result = query.getResultList().get(0);

		return result.getSelectedTT();
	}

	@Override
	public String getSelectedTheme() {
		TypedQuery<ThemesEntity> query = em.createNamedQuery("ThemesEntity.getTheme", ThemesEntity.class);
		ThemesEntity result = query.getResultList().get(0);

		return result.getSelectedTheme();
	}

	@Override
	public List<String> getTexts() {
		TypedQuery<ThemesEntity> query = em.createNamedQuery("ThemesEntity.getTheme", ThemesEntity.class);
		ThemesEntity result = query.getResultList().get(0);
		return null;
	}

	@Override
	public List<String> getTextsComplete() {
		TypedQuery<ThemesEntity> query = em.createNamedQuery("ThemesEntity.getTheme", ThemesEntity.class);
		ThemesEntity result = query.getResultList().get(0);
		return null;

	}

	@Override
	public void saveTheme(String selectedTT, String selectedTheme) {
	}

	@Override
	public String getSelectedLogo() {
		TypedQuery<ThemesEntity> query = em.createNamedQuery("ThemesEntity.getTheme", ThemesEntity.class);
		ThemesEntity result = query.getResultList().get(0);
		return result.getSelectedLogo();
	}

}
