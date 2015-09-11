package pt.criticalsoftware.service.business;

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

}
