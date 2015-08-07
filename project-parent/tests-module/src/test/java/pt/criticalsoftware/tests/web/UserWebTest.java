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

import pt.criticalsoftware.platform.login.Login;
import pt.criticalsoftware.platform.login.Register;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.tests.web.utils.ContextMocker;

@RunWith(Arquillian.class)
public class UserWebTest {
	
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
	private Login login;
	@EJB
	private IUserBusinessService userservice;
	
	@Before
	public void setUp() {
		FacesContext context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		HttpSession mockSession = mock(HttpSession.class);
		when(context.getExternalContext()).thenReturn(ext);
		Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
	}
	
	@Test
	public void loginTest() {
		login.setUsername("username");
		login.setPassword("password");
		String landingpage = login.login();
		assertEquals("loginerror",landingpage);
	}
	
	@Test
	public void registerEmailFailTest() {
		boolean exceptionThrown = false;
		try {
			userservice.createUser("username", "1234", "abc@email.com", "A", "BC", Role.GESTOR);
		} catch (DuplicateEmailException e) {
			exceptionThrown = true;
		} catch (DuplicateUsernameException e) {
			exceptionThrown = false;
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void registerUsernameFailTest() {
		boolean exceptionThrown = false;
		try {
			userservice.createUser("abc", "1234", "email", "A", "BC", Role.GESTOR);
		} catch (DuplicateEmailException e) {
			exceptionThrown = false;
		} catch (DuplicateUsernameException e) {
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}

}
