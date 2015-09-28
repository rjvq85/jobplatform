package pt.criticalsoftware.service.persistence;

import java.util.List;

import pt.criticalsoftware.service.model.IEmail;

public interface IEmailPersistenceService {
	
	IEmail getActiveSettings();

	IEmail addSettings(IEmail newSettings);

	List<IEmail> getAll();

	IEmail findById(int id);

	IEmail changeSettings(IEmail settings);

	List<IEmail> getInactiveSettings();

}
