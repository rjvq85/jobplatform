package pt.criticalsoftware.domain.proxies;


import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.IEmail;
import pt.criticalsoftware.service.model.IEmailBuilder;
import pt.criticalsoftware.service.persistence.utils.PasswordSecurity;

@Stateless
public class EmailBuilder implements IEmailBuilder {

	private EmailProxy email;
	
	public EmailBuilder() {
		this.email = new EmailProxy();
	}

	@Override
	public IEmailBuilder hostname(String hostname) {
		email.setHostName(hostname);
		return this;
	}

	@Override
	public IEmailBuilder smtpPort(Integer smtpPort) {
		email.setSmtpPort(smtpPort);
		return this;
	}
	
	@Override
	public IEmailBuilder username(String username) {
		email.setUsername(username);
		return this;
	}

	@Override
	public IEmailBuilder password(String password) {
		PasswordSecurity ps;
		try {
			ps = new PasswordSecurity();
			String encryptedPassword = ps.encrypt(password);
			email.setPassword(encryptedPassword);
		} catch (Exception e) {
			System.out.println("Erro ao encriptar password!");
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public IEmailBuilder sslOnConnect(Boolean sslonconnect) {
		email.setSllOnConnect(sslonconnect);
		return this;
	}

	@Override
	public IEmailBuilder startTLS(Boolean starttls) {
		email.setStartTLS(starttls);
		return this;
	}

	@Override
	public IEmailBuilder active(Boolean active) {
		email.setActive(active);
		return this;
	}
	
	@Override
	public IEmail build() {
		return email;
	}

}
