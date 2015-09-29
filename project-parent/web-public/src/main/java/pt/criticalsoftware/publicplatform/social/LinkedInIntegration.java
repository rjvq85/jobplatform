package pt.criticalsoftware.publicplatform.social;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ILinkedInBuilder;

@Named
@SessionScoped
public class LinkedInIntegration implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ILinkedInBuilder linkedIn;
	@EJB
	private ICandidateBusinessService candidateBness;

	private static final String PROFILE_URL = "https://api.linkedin.com/v1/people/~:(num-connections,picture-url,summary,public-profile-url)?format=json";
	private String code;
	private OAuthService service;
	private Token requestToken;
	private Token accessToken;
	private Verifier verif;

	public String getAuthUrl() {
		service = new ServiceBuilder().provider(LinkedInApi.class).apiKey("77pdfqun8ckrjv") // for
																							// full-profile
																							// it
																							// should
																							// be
																							// .provider(LinkedIn.withScopes("r_fullprofile")),
																							// but
																							// a
																							// special
																							// authorization
																							// is
																							// needed.
				.apiSecret("wbgAQM5dT84K43Rm").build();
		requestToken = service.getRequestToken();
		return service.getAuthorizationUrl(requestToken);
	}

	public String submit() {
		try {
			verif = new Verifier(code);
			accessToken = service.getAccessToken(requestToken, verif);
			OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_URL);
			service.signRequest(accessToken, request);
			Response response = request.send();
			String responseBody = response.getBody();
			JSONObject responseJson = new JSONObject(responseBody);
			String headline = responseJson.optString("headline", "Não existe");
			String pictureUrl = responseJson.optString("pictureUrl", "Não existe");
			String profileUrl = responseJson.optString("publicProfileUrl", "Não existe");
			String summary = responseJson.optString("summary", "Não existe");
			Integer numConnections = responseJson.optInt("numConnections", 0);

			System.out.println("\nHeadline: " + headline + "\n\n" + "pictureUrl: " + pictureUrl + "\n\n");
			ICandidate candidate = candidateBness.getCandidateById((Integer) getSession().getAttribute("userId"));
			candidate.setLinkedInConnections(numConnections);
			candidate.setLinkedInHeadline(headline);
			candidate.setLinkedInPicture(pictureUrl);
			candidate.setLinkedInSummary(summary);
			candidate.setLinkedInUrl(profileUrl);
			candidateBness.updateCandidate(candidate);
			FacesContext.getCurrentInstance().addMessage("linkedinintegration",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "A operação foi realizada com sucesso"));
			return "jobs";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("linkedinintegration",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro"));
			return null;
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private HttpSession getSession() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return req.getSession();
	}

}
