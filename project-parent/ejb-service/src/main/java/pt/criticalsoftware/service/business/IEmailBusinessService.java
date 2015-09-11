package pt.criticalsoftware.service.business;

import pt.criticalsoftware.service.model.IEmail;

public interface IEmailBusinessService {

	IEmail getActive();
	
	IEmail addSettings(IEmail newSettings);

}
