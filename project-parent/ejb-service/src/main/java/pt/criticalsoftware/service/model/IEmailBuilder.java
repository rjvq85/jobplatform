package pt.criticalsoftware.service.model;


public interface IEmailBuilder {

	IEmailBuilder hostname(String hostname);

	IEmailBuilder smtpPort(Integer smtpPort);

	IEmailBuilder sslOnConnect(Boolean sslonconnect);

	IEmailBuilder startTLS(Boolean starttls);

	IEmailBuilder active(Boolean active);

	IEmail build();

	IEmailBuilder username(String username);

	IEmailBuilder password(String password);

}
