package pt.criticalsoftware.tests.web;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.io.impl.Base64;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pt.criticalsoftware.platform.admin.MailSettingsWeb;
import pt.criticalsoftware.platform.candidacy.AssignCandidacy;
import pt.criticalsoftware.platform.interviews.ManageInterview;
import pt.criticalsoftware.platform.interviews.NewInterview;
import pt.criticalsoftware.platform.position.NewPosition;
import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.IEmailBusinessService;
import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.business.INotificationBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ICandidateBuilder;
import pt.criticalsoftware.service.model.IEmail;
import pt.criticalsoftware.service.model.IEmailBuilder;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IInterviewBuilder;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.persistence.states.PositionState;
import pt.criticalsoftware.service.sources.CandidacySource;
import pt.criticalsoftware.tests.web.utils.ContextMocker;

@RunWith(Arquillian.class)
public class PositionTest {

	@Deployment
	public static Archive<?> createDeployment() {
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importDependencies(ScopeType.PROVIDED).resolve()
				.withoutTransitivity().asFile();

		WebArchive res = ShrinkWrap.create(WebArchive.class, "test.war");
		for (File file : libs) {
			res.addAsLibrary(file);
		}
		res.addPackages(true, "alehro.p1").addAsResource("test-persistence.xml").addClass(DozerBeanMapper.class)
				.addClass(Mapper.class).addClass(ContextMocker.class).addClass(Base64.class).addClass(Email.class)
				.addClass(EmailException.class).addClass(DynaFormControl.class).addClass(DynaFormModel.class)
				.addClass(RequestContext.class).addClass(SimpleEmail.class).addClass(DefaultAuthenticator.class)
				.addClass(Email.class)
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importTestDependencies().resolve()
						.withTransitivity().asFile())
				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return res;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(PositionTest.class);

	@Inject
	NewPosition newpos;
	@EJB
	IUserBuilder uBuilder;
	@EJB
	IPositionBusinessService posBness;
	@EJB
	IUserBusinessService uBness;
	@Inject
	MailSettingsWeb mailWeb;
	@EJB
	INotificationBusinessService notifBness;
	@EJB
	IEmailBuilder mailBuilder;
	@EJB
	IEmailBusinessService mailBness;
	@Inject
	NewInterview newInterv;
	@EJB
	ICandidacyBuilder candidacyBuilder;
	@EJB
	ICandidateBuilder candidateBuilder;
	@EJB
	ICandidacyBusinessService candidacyBness;
	@EJB
	IInterviewBusinessService interviewBness;
	@EJB
	IInterviewBuilder interviewBuilder;
	@Inject
	ManageInterview manageInterview;
	@Inject
	AssignCandidacy assign;
	@EJB
	ICandidateBusinessService candidateBness;

	@Before
	public void setUp() {
		FacesContext context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		RequestContext reqCont = mock(RequestContext.class);
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		HttpSession mockSession = mock(HttpSession.class);
		when(context.getExternalContext()).thenReturn(ext);
		Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
		Mockito.doNothing().when(reqCont).addCallbackParam("saved", true);
		Mockito.doNothing().when(reqCont).addCallbackParam("saved", false);
		when(mockRequest.getContextPath()).thenReturn("pathpathpath");
	}

	@Ignore
	public void createPosition() {
		
//		// E-mail
//		IEmail newMail = mailBuilder.active(true).hostname("smtp.gmail.com").password("emiliaricardo").smtpPort(465).sslOnConnect(true).startTLS(false).username("pfinal.aor@gmail.com").build();
//		mailBness.addSettings(newMail);
//		// Responsable
//		IUser user = uBuilder.email("ricardojvquirino@gmail.com").firstName("PN").lastName("LN").password("1234").role(Role.ADMIN)
//				.username("g3stor").build();
//		try {
//			IUser responsable = uBness.createUser(user);
//			newpos.setResponsable(responsable);
//		} catch (DuplicateEmailException | DuplicateUsernameException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String cDateStr = "01-12-2015";
//		Date cDate;
//		try {
//			cDate = new SimpleDateFormat("dd-MM-yyyy").parse(cDateStr);
//			newpos.setCloseDate(cDate);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		newpos.setTitle("Nova posição");
//		newpos.setLocale("Porto");
//		newpos.setCompany("Critical");
//		newpos.setDescription("Bla bla");
//		newpos.setState(PositionState.ABERTA);
//		newpos.setTechnicalArea(TechnicalAreaType.INTEGRATION);
//		newpos.setSla("1semna");
//		newpos.setVacancies(1);
//		newpos.setAdChannels(new ArrayList<String>());
//		newpos.createPosition();
//		List<INotification> notifications = notifBness.getAll();
//		String notifText = notifications.get(0).getSituation();
//		assertEquals("Nova Posição",notifText);
	}
	
	@Test
	public void createInterview() {
		
		// E-mail
		IEmail newMail = mailBuilder.active(true).hostname("smtp.gmail.com").password("emiliaricardo").smtpPort(465).sslOnConnect(true).startTLS(false).username("pfinal.aor@gmail.com").build();
		mailBness.addSettings(newMail);
		// Responsable
		IUser user = uBuilder.email("ricardojvquirino@gmail.com").firstName("PN").lastName("LN").password("1234").role(Role.ADMIN)
				.username("g3stor").build();
		IUser user2 = uBuilder.email("pfinal.aor@gmail.com").firstName("PN").lastName("LN").password("1234").role(Role.ENTREVISTADOR)
				.username("entr3vistador").build();
		try {
			IUser responsable = uBness.createUser(user);
			uBness.createUser(user2);
			newpos.setResponsable(responsable);
		} catch (DuplicateEmailException | DuplicateUsernameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String cDateStr = "01-12-2015";
		Date cDate;
		try {
			cDate = new SimpleDateFormat("dd-MM-yyyy").parse(cDateStr);
			newpos.setCloseDate(cDate);
			newInterv.setDate(cDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newpos.setTitle("Nova posição");
		newpos.setLocale("Porto");
		newpos.setCompany("Critical");
		newpos.setDescription("Bla bla");
		newpos.setState(PositionState.ABERTA);
		newpos.setTechnicalArea(TechnicalAreaType.INTEGRATION);
		newpos.setSla("1semna");
		newpos.setVacancies(1);
		newpos.setAdChannels(new ArrayList<String>());
		newpos.createPosition();
		IPosition pos = posBness.getPositionsByLocale("Porto").get(0);
		// Candidate
		ICandidate candidate = candidateBuilder.address("abc").country("abc").course("abc").cv("abc").degree("abc").email("abc").firstName("abc").lastName("abc").mobile(123)
				.password("abc").phone(123).school("abc").town("abc").username("abc").build();
		candidateBness.addCandidate(candidate);
		ICandidate newcandidate = candidateBness.getCandidateByUsername(candidate.getUsername());
		//Candidacy
		assign.setLetter("abc");
		assign.setPosition(pos);
		assign.setSource(CandidacySource.CRITICAL);
		assign.setUsername(newcandidate.getUsername());
		assign.assignCandidacy();
		ICandidacy icand = assign.getAssignedCandidacy();
		List<IUser> interviewers = new ArrayList<>();
		interviewers.add(user);
		interviewers.add(user2);
		IInterview interv1ew = interviewBuilder.date(LocalDate.now()).interviewers(interviewers).position(pos).candidacy(icand).build();
		interviewBness.newInterview(interv1ew);
		IInterview interview = interviewBness.getAllInterviews().get(0);
		interview.setFeedback("lolol");
		manageInterview.updateInterview(interview);
		
		System.out.println("\n\n\n\n# " + interview.getFeedback() + "#\n\n\n\n");
		
		IInterview ntrview = interviewBness.getAllInterviews().get(0);
		
		String fb = ntrview.getFeedback();
		
		assertEquals("lolol",fb);
		
	}

}
