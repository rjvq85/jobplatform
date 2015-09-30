package pt.criticalsoftware.tests.position;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import pt.criticalsoftware.platform.position.NewPosition;
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
				.addClass(Mapper.class).addClass(ContextMocker.class).addClass(Base64.class)
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importTestDependencies().resolve()
						.withTransitivity().asFile())
				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return res;
	}

	@EJB
	ICandidacyBusinessService candidacyBusiness;
	@EJB
	IPositionBusinessService positionBusiness;
	@EJB
	IUserBusinessService userBusiness;
	@EJB
	IPositionBuilder positionBuilder;
	@EJB
	ICandidacyBuilder candidacyBuilder;
	@EJB
	ICandidateBuilder candidateBuilder;
	@EJB
	IUserBuilder userBuilder;
	@Mock
	HttpSession sessionMock;
	@Mock
	HttpServletRequest requestMock;
	@Inject
	private NewPosition newPosition;

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
	public void positionTest() {
		IUser manager = userBuilder.email("manager@jobs.com").firstName("Ricardo").lastName("Quirino")
				.password("managerpassword").role(Role.GESTOR).username("themanager").build();
		try {
			manager = userBusiness.createUser(manager);
		} catch (DuplicateEmailException | DuplicateUsernameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(2015, 11, 1);
		newPosition.setOpenDate(LocalDate.now().plusWeeks(2));
		newPosition.setCloseDate(calendar.getTime());
		newPosition.setTitle("Position's Title");
		newPosition.setDescription("Position's description");
		newPosition.setLocale("Porto");
		newPosition.setState(PositionState.ABERTA);
		newPosition.setCompany("Critical");
		newPosition.setResponsable(manager);
		newPosition.setTechnicalArea(TechnicalAreaType.INTEGRATION);
		newPosition.setSla(2);
		newPosition.setVacancies(2);
		newPosition.setAdChannels(new ArrayList<String>());
		newPosition.createPosition();
		
		List<IPosition> positions = positionBusiness.getPositionsByLocale("Porto");
		assertEquals(1,positions.size());
	}

}