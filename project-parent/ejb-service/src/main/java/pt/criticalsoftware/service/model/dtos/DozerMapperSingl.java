package pt.criticalsoftware.service.model.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.dozer.DozerBeanMapper;

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
	
	@EJB
	private IUserPersistenceService persistence;
	@Inject
	private IUserBuilder builder;
	
	@PostConstruct
	void mapping() {
		IUser admin = builder
				.email("admin@email.com")
				.firstName("Administrador")
				.lastName("do Sistema")
				.password("123456")
				.role(Role.ADMIN)
				.username("ricardo")
				.build();
		persistence.create(admin);
		dozermapping.add("META-INF/dtomapping.xml");
	}
	
	public static DozerBeanMapper getInstance(){
	    return MapperHolder.instance;
	}

	private static class MapperHolder {
	    static final DozerBeanMapper instance = new DozerBeanMapper(DozerMapperSingl.dozermapping);
	}

}
