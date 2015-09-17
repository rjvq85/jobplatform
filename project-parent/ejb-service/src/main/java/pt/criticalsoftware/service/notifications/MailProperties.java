package pt.criticalsoftware.service.notifications;


import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.utils.PasswordSecurity;

public class MailProperties {

	private Email email;
	
	public MailProperties() {
		this(null);
	}
	
	public MailProperties(Email email) {
		this.email = email != null ? email : new SimpleEmail();
	}

	public MailProperties hostname(String hostname) {
		System.out.println(hostname);
		email.setHostName(hostname);
		return this;
	}

	public MailProperties smtpPort(Integer smtpPort) {
		email.setSmtpPort(smtpPort);
		return this;
	}
	
	public MailProperties auth(String username, String password) {
		String decryptedPassword;
		try {
			decryptedPassword = new PasswordSecurity().decrypt(password);
			email.setAuthentication(username, decryptedPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public MailProperties sslConnect(Boolean sslOnConnect) {
		email.setSSLOnConnect(sslOnConnect);
		return this;
	}
	
	public MailProperties startTLS(Boolean startTLS) {
		email.setStartTLSEnabled(startTLS);
		return this;
	}
	
	public MailProperties setFrom(String username) {
		try {
			email.setFrom(username);
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public MailProperties setSubject(String subject) {
		email.setSubject(subject);
		return this;
	}
	
	public MailProperties setMsg(String msg) {
		try {
			email.setMsg(msg);
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public MailProperties setReceiver(String receiver) {
		try {
			email.addTo(receiver);
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public Email getEmail() {
		return email;
	}
	
	public void setEmail(Email email) {
		this.email = email;
	}

	public MailProperties setReceivers(String[] addresses) throws EmailException {
		email.addTo(addresses);
		return this;
	}
	
	
}
