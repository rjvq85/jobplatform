package pt.criticalsoftware.service.notifications;

import java.util.List;

import org.apache.commons.mail.EmailException;

import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;

public interface IMailSender {

	void sendEmail(IPosition position, IUser user) throws EmailException;

	void sendEmail(IInterview interview, IUser user, String path);

	void sendEmail(ICandidacy cand, IUser user, Integer origin);

	void sendEmail(IInterview createdInterview, List<IUser> interviewers, String path);

	void sendResetPasswordEmail(String path, String email, String token) throws EmailException;

	void sendAttachmentEmail(IUser user, String path);

}
