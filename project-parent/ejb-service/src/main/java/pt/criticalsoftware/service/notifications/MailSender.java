package pt.criticalsoftware.service.notifications;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.mail.*;

import pt.criticalsoftware.service.business.IEmailBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IEmail;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;

@Stateless
public class MailSender implements IMailSender {

	private Email email;
	private MailProperties mailProp;
	@EJB
	private IEmailBusinessService business;

	@PostConstruct
	public void setupEmail() {
		mailProp = new MailProperties();
		IEmail activeEmail = business.getActive();
		email = mailProp.hostname(activeEmail.getHostName()).smtpPort(activeEmail.getSmtpPort())
					.auth(activeEmail.getUsername(),activeEmail.getPassword())
					.sslConnect(activeEmail.getSllOnConnect()).startTLS(activeEmail.getStartTLS()).setFrom(activeEmail.getUsername()).getEmail();
		
	}
	
	@Override
	public void sendEmail(IPosition position, IUser user) {
		String receiver = user.getEmail();
		String subject = "Gestor de nova Posição";
		String msg = "Estimado/a " + user.getFirstName() + " " + user.getLastName() + ",\n" 
		+ "\nFoi definido como gestor da nova posição:"
		+ "\nTítulo: " + position.getTitle() + "\n"
				+ "Descrição: " + position.getDescription() + "\n"
						+ "\nA referida posição encerrará na data: " + new SimpleDateFormat("dd-MM-yyyy").format(position.getCloseDate()) + "\n"
								+ "\nEsta posição tem a referência: " + position.getReference();
		email = mailProp.setSubject(subject).setReceiver(receiver).setMsg(msg).getEmail();
		try {
			email.setStartTLSEnabled(false);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendEmail(ICandidacy cand, IUser user) {
		String receiver = user.getEmail();
	}
	
	@Override
	public void sendEmail(IInterview interview, IUser user) {
		String receiver = user.getEmail();
	}

}
