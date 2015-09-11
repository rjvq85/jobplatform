package pt.criticalsoftware.domain.proxies;

import pt.criticalsoftware.domain.entities.EmailEntity;
import pt.criticalsoftware.service.model.IEmail;

public class EmailProxy implements IEntityAware<EmailEntity>, IEmail {
	
	private EmailEntity email;
	
	public EmailProxy() {
		this(null);
	}
	
	public EmailProxy(EmailEntity entity) {
		email = (null != entity) ? entity : new EmailEntity();
	}
	
	@Override
	public String getHostName() {
		return email.getHostName();
	}

	@Override
	public void setHostName(String hostName) {
		email.setHostName(hostName);
	}

	@Override
	public Integer getSmtpPort() {
		return email.getSmtpPort();
	}

	@Override
	public void setSmtpPort(Integer smtpPort) {
		email.setSmtpPort(smtpPort);
	}

	@Override
	public String getUsername() {
		return email.getUsername();
	}

	@Override
	public void setUsername(String username) {
		email.setUsername(username);
	}

	@Override
	public String getPassword() {
		return email.getPassword();
	}

	@Override
	public void setPassword(String password) {
		email.setPassword(password);
	}

	@Override
	public Boolean getSllOnConnect() {
		return email.getSllOnConnect();
	}

	@Override
	public void setSllOnConnect(Boolean sllOnConnect) {
		email.setSllOnConnect(sllOnConnect);
	}

	@Override
	public Boolean getStartTLS() {
		return email.getStartTLS();
	}

	@Override
	public void setStartTLS(Boolean startTLS) {
		email.setStartTLS(startTLS);
	}

	@Override
	public Integer getId() {
		return email.getId();
	}

	@Override
	public EmailEntity getEntity() {
		return email;
	}
	
	@Override
	public Boolean getActive() {
		return email.getActive();
	}

	@Override
	public void setActive(Boolean active) {
		email.setActive(active);
	}
	
	@Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && email.getId() != null)
            ? email.getId().equals(((EmailProxy) other).getId())
            : (other == this);
    }

    @Override
    public int hashCode() {
        return (email.getId() != null) 
            ? (getClass().hashCode() + email.getId().hashCode())
            : super.hashCode();
    }
	
	

}
