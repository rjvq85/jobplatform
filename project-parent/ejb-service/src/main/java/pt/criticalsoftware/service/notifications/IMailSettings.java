package pt.criticalsoftware.service.notifications;

public interface IMailSettings {

	IMailSettings hostName(String hostName);

	IMailSettings smtpPort(String smtpPort);

	IMailSettings username(String username);

	IMailSettings password(String password);

	IMailSettings sslOnConnect(Boolean sslConnect);

	void update();
	

}
