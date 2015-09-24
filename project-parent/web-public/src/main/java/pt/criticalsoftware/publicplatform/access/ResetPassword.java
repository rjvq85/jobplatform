package pt.criticalsoftware.publicplatform.access;

import java.time.LocalDate;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.ITokenBusinessService;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IToken;
import pt.criticalsoftware.service.model.ITokenBuilder;
import pt.criticalsoftware.service.notifications.IMailSender;

@Named
@RequestScoped
public class ResetPassword {

	@EJB
	private ITokenBusinessService tokenBness;
	@EJB
	private ICandidateBusinessService candidateBness;
	@EJB
	private ITokenBuilder builder;
	@EJB
	private IMailSender mailSender;

	private ICandidate candidate;
	private String password;
	private String email;
	private String originalEmail;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void defineCandidate() {
		IToken tk = tokenBness.getToken(token);
		if (null != tk.getExpiringDate()) {
			if (LocalDate.now().isBefore(tk.getExpiringDate())) {
				email = tk.getEmail();
				candidate = candidateBness.getCandidateByEmail(email);
			}
		} else {
			candidate = null;
		}
	}

	public String reset() {
		try {
			IToken tk = tokenBness.getToken(token);
			candidate = candidateBness.getCandidateByEmail(tk.getEmail());
			candidateBness.updatePassword(password, candidate);
			tokenBness.remove(tk.getToken());
			return "passwordreset";
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
			return null;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		IToken tk = tokenBness.getToken(token);
		this.email = tk.getEmail();
	}

	public void generateToken() {

	}

	public String getOriginalEmail() {
		return originalEmail;
	}

	public void setOriginalEmail(String originalEmail) {
		this.originalEmail = originalEmail;
	}

	public void sendEmail() {
		try {
			ICandidate cndt = candidateBness.getCandidateByEmail(originalEmail);
			if (null == cndt || null == cndt.getUsername()) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail não encontrado.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				IToken builtToken = builder.email(originalEmail).build();
				String genToken = builtToken.getToken();
				tokenBness.createToken(builtToken);
				mailSender.sendResetPasswordEmail(getPath(), originalEmail, genToken);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail enviado.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public Boolean isValid() {
		IToken tk = tokenBness.getToken(token);
		if (null == tk) {
			return false;
		} else return true;
	}

	private String getPath() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		return path;
	}

	public ICandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(ICandidate candidate) {
		this.candidate = candidate;
	}

}
