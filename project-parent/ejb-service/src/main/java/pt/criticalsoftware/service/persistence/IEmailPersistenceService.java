package pt.criticalsoftware.service.persistence;

import pt.criticalsoftware.service.model.IEmail;

public interface IEmailPersistenceService {
	
	IEmail getActiveSettings();

	IEmail addSettings(IEmail newSettings);

}
