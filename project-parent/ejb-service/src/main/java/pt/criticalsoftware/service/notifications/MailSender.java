package pt.criticalsoftware.service.notifications;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IEmailBusinessService;
import pt.criticalsoftware.service.business.INotificationBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IEmail;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;

@Stateless
public class MailSender implements IMailSender {

	private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

	private Email email;
	private MailProperties mailProp;
	@EJB
	private IEmailBusinessService business;
	@EJB
	private INotificationBusinessService notifBness;

	@PostConstruct
	public void setupEmail() {
		try {
			mailProp = new MailProperties();
			IEmail activeEmail = business.getActive();
			email = mailProp.hostname(activeEmail.getHostName()).smtpPort(activeEmail.getSmtpPort())
					.auth(activeEmail.getUsername(), activeEmail.getPassword())
					.sslConnect(activeEmail.getSllOnConnect()).startTLS(activeEmail.getStartTLS())
					.setFrom(activeEmail.getUsername()).getEmail();
		} catch (Exception e) {
			logger.error("Erro ao definir propriedades de Email.");
		}
	}

	@Override
	public void sendEmail(IPosition position, IUser user) throws EmailException {
		try {
			String receiver = user.getEmail();
			String subject = "Gestor de nova Posição";
			String msg = "Estimado/a " + user.getFirstName() + " " + user.getLastName() + ",\n"
					+ "\nFoi definido como gestor da nova posição:" + "\nTítulo: " + position.getTitle() + "\n"
					+ "Descrição: " + position.getDescription() + "\n" + "\nA referida posição encerrará na data: "
					+ new SimpleDateFormat("dd-MM-yyyy").format(position.getCloseDate()) + "\n"
					+ "\nEsta posição tem a referência: " + position.getReference();
			email = setDetails(subject, receiver, msg);
			String text = "Posição: " + position.getReference();
			notifBness.createNotification(Situation.NEWPOSITION.description, user, text);
			email.send();
		} catch (Exception e) {
			logger.error("Erro ao enviar notificação por e-mail: Nova Posição - " + position.getReference()
					+ ", Responsável: " + user.getUsername());
		}
	}

	@Override
	public void sendEmail(ICandidacy cand, IUser user, Integer origin) {
		if (origin == 1) {
			try {
				String receiver = user.getEmail();
				String subject = "Candidatura a Posição";
				String msg = "Estimado/a " + user.getFirstName() + " " + user.getLastName() + ",\n"
						+ "\nFoi atribuída uma candidatura à posição com a referência "
						+ cand.getPositionCandidacy().getReference()
						+ ", na qual está definido/a como responsável.\n\nCandidato: "
						+ cand.getCandidate().getFirstName() + " " + cand.getCandidate().getLastName()
						+ "\n\nEsta candidatura tem a referência: " + cand.getReference();
				email = setDetails(subject, receiver, msg);
				String text = "Candidatura: " + cand.getReference();
				notifBness.createNotification(Situation.ASSIGNCANDIDACY.description, user, text);
				email.send();
			} catch (EmailException e) {
				logger.error("Erro ao enviar notificação por e-mail: Atribuição de Candidatura - " + cand.getReference()
						+ ", Responsável: " + user.getUsername());
				e.printStackTrace();
			}
		} else if (origin == 2) {
			try {
				String receiver = user.getEmail();
				String subject = "Candidatura a Posição";
				String msg = "Estimado/a " + user.getFirstName() + " " + user.getLastName() + ",\n"
						+ "\nFoi realizada uma candidatura à posição com a referência "
						+ cand.getPositionCandidacy().getReference()
						+ ", na qual está definido/a como responsável.\n\nCandidato: "
						+ cand.getCandidate().getFirstName() + " " + cand.getCandidate().getLastName()
						+ "\n\nEsta candidatura tem a referência: " + cand.getReference();
				email = setDetails(subject, receiver, msg);
				String text = "Candidatura: " + cand.getReference();
				notifBness.createNotification(Situation.NEWCANDIDACY.description, user, text);
				email.send();
			} catch (EmailException e) {
				logger.error("Erro ao enviar notificação por e-mail: Criação de Candidatura - " + cand.getReference()
						+ ", Responsável: " + user.getUsername());
				e.printStackTrace();
			}
		} else {
			logger.warn("Origem desconhecida: Nova candidatura/Atribuição de candidatura");
		}
	}

	@Override
	public void sendEmail(IInterview interview, IUser user, String path) {
		try {
			String receiver = user.getEmail();
			String subject = "Nova Entrevista";
			String msg = "Estimado/a " + user.getFirstName() + " " + user.getLastName() + ",\n"
					+ "\nFoi agendada uma entrevista a uma candidatura com a referência " + interview.getReference()
					+ ", na qual está definido/a como entrevistador(a).\n\nPerfil do Candidato: " + path
					+ "/Authorized/Candidates/candidateprofile.xhtml?candidato="
					+ interview.getCandidacy().getCandidate().getId() + "\n\nEsta entrevista tem a referência: "
					+ interview.getReference();
			email = setDetails(subject, receiver, msg);
			String text = "Entrevista: " + interview.getReference();
			notifBness.createNotification(Situation.NEWINTERVIEW.description, user, text);
			email.send();
		} catch (EmailException e) {
			logger.error("Erro ao enviar notificação por e-mail: Entrevista de Candidatura - "
					+ interview.getReference() + ", Responsável: " + user.getUsername());
			e.printStackTrace();
		}
	}

	@Override
	public void sendEmail(IInterview createdInterview, List<IUser> interviewers, String path) {
		String[] addresses = new String[interviewers.size()];
		for (int i = 0; i < addresses.length; i++) {
			addresses[i] = interviewers.get(i).getEmail();
		}
		try {
			String subject = "Nova Entrevista";
			String msg = "Foi agendada uma entrevista a uma candidatura com a referência " + createdInterview.getReference()
					+ ", na qual está definido/a como entrevistador(a).\n\nPerfil do Candidato: " + path
					+ "/Authorized/Candidates/candidateprofile.xhtml?candidato="
					+ createdInterview.getCandidacy().getCandidate().getId() + "\n\nEsta entrevista tem a referência: "
					+ createdInterview.getReference();
			email = setDetails(subject, addresses, msg);
			email.send();
		} catch (EmailException e) {
			logger.error("Erro ao enviar notificação por e-mail: Entrevista de Candidatura - "
					+ createdInterview.getReference());
			e.printStackTrace();
		}
		String text = "Entrevista: " + createdInterview.getReference();
		notifBness.createNotification(Situation.NEWINTERVIEW.description, interviewers, text);
	}

	private Email setDetails(String subject, String[] addresses, String msg) {
		try {
			return mailProp.setSubject(subject).setMsg(msg).setReceivers(addresses).getEmail();
		} catch (EmailException e) {
			logger.error("Erro ao definir parâmetros do e-mail a enviar para vários endereços.");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void sendResetPasswordEmail(String path, String email, String token) {
		try {
			this.email.addTo(email);
			String subject = "Alterar password da conta";
			String msg = "Foi feito um pedido para alterar a password para a conta associada a este e-mail.\n"
					+ "Se não fez este pedido, por favor ignore este e-mail.\n"
					+ "\nCaso contrário, clique neste link: " + path + "/resetpassword.xhtml?token=" + token + "\n\n"
							+ "Obrigado.";
			this.email.setSubject(subject);
			this.email.setMsg(msg);
			this.email.send();
		} catch (EmailException e) {
			logger.error("Erro ao enviar notificação por e-mail: Password Reset - "
					+ email);
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendAttachmentEmail(IUser user, String path) {
		try {
			// Attachment
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(path);
			attachment.setDescription("Relatórios em PDF");
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setName(user.getFirstName() + " " +user.getLastName());
			
			// Email Settings
			IEmail activeMail = business.getActive();
			MultiPartEmail mpEmail = new MultiPartEmail();
			mpEmail.setHostName(activeMail.getHostName());
			mpEmail.setSmtpPort(activeMail.getSmtpPort());
			mpEmail.setAuthentication(activeMail.getUsername(), activeMail.getPassword());
			mpEmail.setSSLOnConnect(activeMail.getSllOnConnect());
			mpEmail.setStartTLSEnabled(activeMail.getStartTLS());
			mpEmail.addTo(user.getEmail());
			mpEmail.setSubject("[Critical Jobs] Relatório PDF");
			mpEmail.setMsg("Relatório em PDF como pedido.\n\nEsta mensagem foi gerada automaticamente.");
			mpEmail.setFrom(activeMail.getUsername());
			mpEmail.attach(attachment);
			
			mpEmail.send();
		} catch (EmailException e) {
			logger.error("Erro ao enviar PDF de relatório por e-mail");
			e.printStackTrace();
		}
	}

	private Email setDetails(String subject, String receiver, String msg) {
		return mailProp.setSubject(subject).setReceiver(receiver).setMsg(msg).getEmail();
	}

	private enum Situation {
		NEWPOSITION("Nova Posição"), ASSIGNCANDIDACY("Atribuição de Candidatura a Posição"), NEWCANDIDACY(
				"Candidato submeteu candidatura via site público"), NEWINTERVIEW("Marcação de entrevista");

		private String description;

		private Situation(String d) {
			this.description = d;
		}

		@SuppressWarnings("unused")
		public String getDescription() {
			return description;
		}

	}


}
