package pt.criticalsoftware.platform.admin;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IEmailBusinessService;
import pt.criticalsoftware.service.model.IEmail;
import pt.criticalsoftware.service.model.IEmailBuilder;

@Named
@RequestScoped
public class MailSettingsWeb {

	private static final Logger logger = LoggerFactory.getLogger(MailSettingsWeb.class);

	@EJB
	private IEmailBusinessService business;
	@EJB
	private IEmailBuilder builder;

	private String hostname;
	private Integer smtpPort;
	private String username;
	private String password;
	private Boolean sslOnConnect;
	private Boolean startTLS;
	private Boolean active;
	private Boolean testResult;

	public void newSettings() {
		try {
			IEmail newSettings = builder.hostname(hostname).smtpPort(smtpPort).username(username).password(password)
					.sslOnConnect(sslOnConnect).startTLS(startTLS).active(active).build();
			business.addSettings(newSettings);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Novas definições guardadas com sucesso.",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("saved", true);
		} catch (Exception e) {
			logger.error("Erro ao tentar guardar novas definições de e-mail");
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao guardar novas definições", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("saved", false);
		}
	}

	public void clear() {
		hostname = null;
		smtpPort = null;
		username = null;
		password = null;
		sslOnConnect = null;
		startTLS = null;
		active = null;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getSslOnConnect() {
		return sslOnConnect;
	}

	public void setSslOnConnect(Boolean sslOnConnect) {
		this.sslOnConnect = sslOnConnect;
	}

	public Boolean getStartTLS() {
		return startTLS;
	}

	public void setStartTLS(Boolean startTLS) {
		this.startTLS = startTLS;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getTestResult() {
		return testResult;
	}

	public void setTestResult(Boolean testResult) {
		this.testResult = testResult;
	}

}
