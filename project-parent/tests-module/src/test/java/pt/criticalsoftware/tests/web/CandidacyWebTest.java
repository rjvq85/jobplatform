package pt.criticalsoftware.tests.web;

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
import org.mockito.Mockito;

import com.sun.syndication.io.impl.Base64;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import java.io.File;

import pt.criticalsoftware.platform.candidacy.CandidacyListView;
import pt.criticalsoftware.platform.candidacy.NewCandidacy;
import pt.criticalsoftware.platform.login.Login;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidateBuilder;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.persistence.states.CandidacyState;
import pt.criticalsoftware.tests.web.utils.ContextMocker;

@RunWith(Arquillian.class)
public class CandidacyWebTest {
	
	@Deployment
	public static Archive<?> createDeployment() {
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importDependencies(ScopeType.PROVIDED).resolve().withoutTransitivity().asFile();

        WebArchive res = ShrinkWrap.create(WebArchive.class, "test.war");
        for(File file : libs){
            res.addAsLibrary(file);
        }
        res.addPackages(true, "alehro.p1")
        .addAsResource("test-persistence.xml")
        .addClass(DozerBeanMapper.class)
        .addClass(Mapper.class)
        .addClass(ContextMocker.class)
        .addClass(Base64.class)
        .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importTestDependencies().resolve().withTransitivity().asFile())        
        .addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml")
		.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        return res;          
	}
	
	@Inject
	CandidacyListView list;
	@Inject
	NewCandidacy nc;
	
	@Before
	public void setUp() {
		FacesContext context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		HttpSession mockSession = mock(HttpSession.class);
		when(context.getExternalContext()).thenReturn(ext);
		Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
	}
	
	@Ignore
	public void searchTest() {
		list.setSearchText("Ana Martins");
		list.doSearch();
	}
	
	@Test
	public void createCandidacy() {
		nc.setAddress("Porto");
		nc.setCity("Porto");
		nc.setCountry("Portugal");
		nc.setCourse("Mestrado");
		nc.setCv("CV");
		nc.setDegree("Mestrado");
		nc.setEmail("@@@");
		nc.setFirstName("Pi");
		nc.setLastName("Ul");
		nc.setMobile(321);
		nc.setPassword("1234");
		nc.setPhone(123);
		nc.setSchool("feup");
		nc.setState(CandidacyState.SUBMETIDA);
		nc.setUsername("asdfg");
		nc.create();
		list.setSearchText("pi");
		list.doSearch();
		assertEquals(1,list.getCandidacies().size());
	}

}
