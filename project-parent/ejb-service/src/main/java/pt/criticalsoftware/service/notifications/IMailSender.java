package pt.criticalsoftware.service.notifications;

import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;

public interface IMailSender {

	void sendEmail(IPosition position, IUser user);

	void sendEmail(ICandidacy cand, IUser user);

	void sendEmail(IInterview interview, IUser user);

}
