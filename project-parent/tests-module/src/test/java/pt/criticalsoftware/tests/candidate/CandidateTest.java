package pt.criticalsoftware.tests.candidate;

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
import org.junit.Ignore;
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
import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.exceptions.UniqueConstraintException;
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
public class CandidateTest {

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
	ICandidateBusinessService candidateBusiness;
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

	@Test(expected = DuplicateCandidateException.class)
	public void candidateFailTest() {
		ICandidate candidate = candidateBuilder.address("Rua Qualquer Uma")
				.country("Portugal")
				.course("Engenharia Informática")
				.cv("/caminho/para/o/cv")
				.degree("Licenciatura")
				.email("thecandidate@email.com")
				.firstName("Albeto")
				.lastName("Intestino")
				.mobile(919191919)
				.password("qwerty123")
				.phone(221234567)
				.school("Escola Privada da Cidade")
				.town("Olivença")
				.username("albertointestino")
				.build();
		candidateBusiness.addCandidate(candidate);
		ICandidate otherCandidate = candidateBuilder.address("Rua Qualquer Uma")
				.country("Portugal")
				.course("Engenharia Informática")
				.cv("/caminho/para/o/cv")
				.degree("Licenciatura")
				.email("thecandidate@email.com")
				.firstName("Albeto")
				.lastName("Intestino")
				.mobile(919191919)
				.password("qwerty123")
				.phone(221234567)
				.school("Escola Privada da Cidade")
				.town("Olivença")
				.username("albertointestino")
				.build();
		candidateBusiness.addCandidate(otherCandidate);
	}
	
	@Test
	public void assignCandidacyToCandidate() {
		IPosition position = positionBusiness.getPositionById(46);
		IUser user = userBusiness.getUserByID(1);
		ICandidate candidate = candidateBusiness.getCandidateById(6);
		ICandidacy candidacy = candidacyBuilder.candidate(candidate)
				.motivationalLetter("Motivational Letter")
				.position(position)
				.source("Facebook")
				.state(CandidacyState.SUBMETIDA)
				.build();
		try {
			ICandidacy assignedCandidacy = candidacyBusiness.assignCandidacy(candidacy);
			assertEquals(candidate.getEmail(),assignedCandidacy.getCandidate().getEmail());
		} catch (UniqueConstraintException e) {
			e.printStackTrace();
		}
	}

}