package pt.criticalsoftware.service.model;


public interface IEmail {

	String getHostName();

	void setHostName(String hostName);

	Integer getSmtpPort();

	void setSmtpPort(Integer smtpPort);

	Boolean getSllOnConnect();

	void setSllOnConnect(Boolean sllOnConnect);

	Boolean getStartTLS();

	void setStartTLS(Boolean startTLS);

	Integer getId();

	Boolean getActive();

	void setActive(Boolean active);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

}
