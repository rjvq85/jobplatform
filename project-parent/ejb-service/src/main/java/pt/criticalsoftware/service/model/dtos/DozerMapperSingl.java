package pt.criticalsoftware.service.model.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ejb.LocalBean;
import org.dozer.DozerBeanMapper;
import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidateBuilder;
import org.dozer.Mapper;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.IUserPersistenceService;
import pt.criticalsoftware.service.persistence.roles.Role;

/**
 * Session Bean implementation class DozerMapperSingl
 */
@Singleton
@Startup
public class DozerMapperSingl {

	private static List<String> dozermapping = new ArrayList<>();
	public static Mapper dozerMapper;
	@EJB
	private IUserPersistenceService persistence;
	@Inject
	private IUserBuilder builder;

	@Inject
	private ICandidateBuilder candidate;
	@Inject
	private ICandidacyBuilder candidacy;
	@Inject
	private ICandidacyBusinessService business;

	@PostConstruct
	void mapping() {
		IUser admin = builder.email("admin@email.com").firstName("Administrador").lastName("do Sistema")
				.password("123456").role(Role.ADMIN).username("ricardo").build();
		persistence.create(admin);
//		ICandidate icandidate1 = candidate.address("Porto").country("Portugal").course("Engenharia")
//				.degree("Licenciatura").email("ricardo@email.com").firstName("Ricardo").lastName("Quirino")
//				.mobile(919191919).password("123456").phone(222222222).school("FEUP").town("Porto").username("ricardo")
//				.build();
//		ICandidacy icandidacy1 = candidacy.state(CandidacyState.SUBMETIDA).candidate(icandidate1).build();
//		business.createCandidacy(icandidacy1);
//		try {
//			Thread.sleep(50);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		ICandidate icandidate2 = candidate.address("Porto").country("Portugal").course("Design").degree("Licenciatura")
//				.email("ana@email.com").firstName("Ana").lastName("Martins").mobile(919191919).password("123456")
//				.phone(222222222).school("ESAD").town("Porto").username("ana").build();
//		ICandidacy icandidacy2 = candidacy.state(CandidacyState.SUBMETIDA).candidate(icandidate2).build();
//		business.createCandidacy(icandidacy2);
		dozermapping.add("META-INF/dtomapping.xml");
	}

	public static DozerBeanMapper getInstance() {
		return MapperHolder.instance;

	}

	private static class MapperHolder {
		static final DozerBeanMapper instance = new DozerBeanMapper(DozerMapperSingl.dozermapping);
	}

}
