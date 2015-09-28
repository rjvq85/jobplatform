package pt.criticalsoftware.service.business;

import java.util.List;

import pt.criticalsoftware.service.model.IEmail;

public interface IEmailBusinessService {

	IEmail getActive();
	
	IEmail addSettings(IEmail newSettings);

	List<IEmail> getAll();

	IEmail getEmailById(int id);

	void updateSettings(IEmail selectedEmailSettings);

	List<IEmail> getInactive();

}
