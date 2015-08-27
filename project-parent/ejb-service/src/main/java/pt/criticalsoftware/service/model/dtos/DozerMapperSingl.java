package pt.criticalsoftware.service.model.dtos;

import java.time.LocalDate;
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
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidateBuilder;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IPositionBuilder;

import org.dozer.Mapper;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.IUserPersistenceService;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.persistence.states.PositionState;

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
	@EJB
	private ICandidacyBusinessService business;

	@Inject
	private IPositionBuilder posBuilder;
	@EJB
	private IPositionBusinessService posBness;

	@PostConstruct
	void mapping() {

		IUser admin = builder.email("admin@email.com").firstName("Administrador").lastName("do Sistema")
				.password("123456").role(Role.ADMIN).username("ricardo").build();
		persistence.create(admin);
		IUser gestor = builder.email("gestor@email.com").firstName("Gestor").lastName("de Candidaturas")
				.password("123456").role(Role.GESTOR).username("quirino").build();
		persistence.create(gestor);
		dozermapping.add("META-INF/dtomapping.xml");
	}

	public static DozerBeanMapper getInstance() {
		return MapperHolder.instance;

	}

	private static class MapperHolder {
		static final DozerBeanMapper instance = new DozerBeanMapper(DozerMapperSingl.dozermapping);
	}

}
