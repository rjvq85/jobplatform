package pt.criticalsoftware.platform.admin;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient.AUTH_METHOD;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IEmailBusinessService;
import pt.criticalsoftware.service.business.INotificationBusinessService;
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
	@EJB
	private INotificationBusinessService notifBness;

	private String hostname;
	private Integer smtpPort;
	private String username;
	private String password;
	private Boolean sslOnConnect;
	private Boolean startTLS;
	private Boolean active;
	private Boolean testResult;

	private List<IEmail> existingSettings;
	private IEmail selectedEmailSettings;
	private IEmail activeSettings;

	public void newSettings() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (testSettings()) {
				IEmail newSettings = builder.hostname(hostname).smtpPort(smtpPort).username(username).password(password)
						.sslOnConnect(sslOnConnect).startTLS(startTLS).active(active).build();
				business.addSettings(newSettings);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Novas definições guardadas com sucesso.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				request.addCallbackParam("saved", true);
			} else {
				request.addCallbackParam("saved", false);
			}
		} catch (Exception e) {
			logger.error("Erro ao tentar guardar novas definições de e-mail");
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao guardar novas definições", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			request.addCallbackParam("saved", false);
		}
	}
	
	public void chooseSettings() {
		try {
			selectedEmailSettings.setActive(true);
			business.updateSettings(selectedEmailSettings);
			RequestContext.getCurrentInstance().addCallbackParam("chosen", true);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro.",null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext.getCurrentInstance().addCallbackParam("chosen", false);
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

	private boolean testSettings() {
		FacesMessage msg = new FacesMessage();
		AuthenticatingSMTPClient client = new AuthenticatingSMTPClient("TLS", false);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> future = executor.submit(new Runnable() {

			@Override
			public void run() {
				try {
					client.connect(hostname);
					client.login(hostname);
					if (client.execTLS()) {
						if (client.auth(AUTH_METHOD.LOGIN, username, password)) {
							logger.info("Configurações do mail correctas");
							msg.setSeverity(FacesMessage.SEVERITY_INFO);
							msg.setSummary("Sucesso");
							msg.setDetail("As configurações foram guardadas.");
						} else {
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							msg.setSummary("Erro");
							msg.setDetail("Credenciais erradas.");
							Thread.sleep(6000);
						}
					} else {
						msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						msg.setSummary("Erro");
						msg.setDetail("Problema em verificar 'hostname: " + hostname + "'");
						Thread.sleep(6000);
					}
				} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
					logger.error("Timeout: erro ao verificar propriedades de e-mail.");
				} catch (InterruptedException e) {
					logger.error("Timeout: erro ao verificar propriedades de e-mail.");
				}
			}
		});

		try {
			future.get(5, TimeUnit.SECONDS);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			future.cancel(true);
			executor.shutdown();
			return true;
		} catch (Exception e) {
			logger.error("Timeout: erro ao verificar propriedades de e-mail.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
	}

	public List<IEmail> getExistingSettings() {
		return business.getInactive();
	}

	public void setExistingSettings(List<IEmail> existingSettings) {
		this.existingSettings = existingSettings;
	}

	public IEmail getSelectedEmailSettings() {
		return selectedEmailSettings;
	}

	public void setSelectedEmailSettings(IEmail selectedEmailSettings) {
		this.selectedEmailSettings = selectedEmailSettings;
	}

	public IEmail getActiveSettings() {
		return business.getActive();
	}

	public void setActiveSettings(IEmail activeSettings) {
		this.activeSettings = activeSettings;
	}

}
