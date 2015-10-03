package pt.criticalsoftware.tests.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sun.syndication.io.impl.Base64;

import pt.criticalsoftware.platform.interviews.InterviewListView;
import pt.criticalsoftware.platform.interviews.NewInterview;
import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ICandidateBuilder;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IPositionBuilder;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.persistence.states.CandidacyState;
import pt.criticalsoftware.service.persistence.states.PositionState;
import pt.criticalsoftware.tests.web.utils.ContextMocker;

@RunWith(Arquillian.class)
public class InterviewWebTest {

	@Deployment
	public static Archive<?> createDeployment() {
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importDependencies(ScopeType.PROVIDED).resolve()
				.withoutTransitivity().asFile();

		WebArchive res = ShrinkWrap.create(WebArchive.class, "test.war");
		for (File file : libs) {
			res.addAsLibrary(file);
		}
		res.addPackages(true, "alehro.p1").addAsResource("test-persistence.xml").addClass(DozerBeanMapper.class)
				.addClass(Mapper.class).addClass(ContextMocker.class).addClass(Base64.class)
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importTestDependencies().resolve()
						.withTransitivity().asFile())
				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return res;
	}

	@Inject
	InterviewListView list;
	@Inject
	NewInterview nInterv;
	@EJB
	ICandidacyBusinessService candBness;
	@EJB
	IPositionBusinessService posBness;
	@EJB
	IUserBusinessService userBness;
	@EJB
	IPositionBuilder pos;
	@EJB
	ICandidacyBuilder cand;
	@EJB
	ICandidateBuilder candte;
	@EJB
	IUserBuilder us;
	@Mock
	HttpSession sessionMock;
	@Mock
	HttpServletRequest requestMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		FacesContext context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		when(context.getExternalContext()).thenReturn(ext);
		when(ext.getRequest()).thenReturn(requestMock);
		Mockito.when(requestMock.getSession()).thenReturn(sessionMock);
	}

	@Test
	public void listInterview() {
		Mockito.when(requestMock.isUserInRole("ADMIN")).thenReturn(false);
		ICandidate candidate = candte.address("Endereço").country("Portugal").course("Curso").cv("cv").degree("Grau")
				.email("em@il").firstName("PNome").lastName("UNome").mobile(123).password("1234").phone(1234)
				.school("Escola").town("Cidade").username("utilizado").build();
		IUser user = us.email("em@il").firstName("fName").lastName("lName").password("1234").role(Role.ENTREVISTADOR)
				.username("entrevistador").build();
		IUser user2 = us.email("em@@il").firstName("fName2").lastName("lName2").password("12345")
				.role(Role.ENTREVISTADOR).username("entrevistador2").build();
		try {
			user = userBness.createUser(user);
			user2 = userBness.createUser(user2);
			Thread.sleep(500);
		} catch (DuplicateEmailException | DuplicateUsernameException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		IUser responsable = userBness.getUserByID(userBness.getUserId(user.getUsername()));
		// ID from the user requesting the list
		when(sessionMock.getAttribute("userID")).thenReturn(responsable.getId());
		LocalDate now = LocalDate.now().plusWeeks(2);
		Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
		IPosition position = pos.adChannels(new ArrayList<String>()).closeDate(date).company("Empresa")
				.description("Descrição").locale("Local").openDate(LocalDate.now().minusMonths(1))
				.reference("Referencia").responsable(responsable).sla(2).state(PositionState.ABERTA)
				.technicalArea(TechnicalAreaType.INTEGRATION).title("Titulo").vacancies(2).build();
		try {
			position = posBness.createPosition(position);
			Thread.sleep(500);
		} catch (DuplicateReferenceException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ICandidacy candidacy = cand.candidate(candidate).motivationalLetter("Carta").position(position).source("Fonte")
				.state(CandidacyState.EM_ENTREVISTA).build();
		try {
			candidacy = candBness.createCandidacy(candidacy);
			Thread.sleep(500);
		} catch (DuplicateCandidateException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<IUser> interviewers = new ArrayList<>();
		interviewers.add(responsable);
		List<IInterview> interviews = list.getInterviews();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(responsable.getEmail(),interviews.get(0).getInterviewers().get(0).getEmail());
	}

}
