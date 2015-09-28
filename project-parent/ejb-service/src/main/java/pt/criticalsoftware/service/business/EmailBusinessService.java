package pt.criticalsoftware.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.IEmail;
import pt.criticalsoftware.service.persistence.IEmailPersistenceService;

@Stateless
public class EmailBusinessService implements IEmailBusinessService {
	
	@EJB
	private IEmailPersistenceService persistence;
	
	@Override
	public IEmail getActive() {
		return persistence.getActiveSettings();
	}

	@Override
	public IEmail addSettings(IEmail newSettings) {
		return persistence.addSettings(newSettings);
	}

	@Override
	public List<IEmail> getAll() {
		return persistence.getAll();
	}

	@Override
	public IEmail getEmailById(int id) {
		return persistence.findById(id);
	}

	@Override
	public void updateSettings(IEmail selectedEmailSettings) {
		persistence.changeSettings(selectedEmailSettings);
	}
	
	@Override
	public List<IEmail> getInactive() {
		return persistence.getInactiveSettings();
	}

}
