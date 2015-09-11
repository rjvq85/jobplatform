package pt.criticalsoftware.service.model.dtos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.dozer.DozerBeanMapper;
import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;

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
	@EJB
	private IUserBuilder builder;

	@EJB
	private ICandidacyBusinessService business;

	@EJB
	private IPositionBusinessService posBness;

	@PostConstruct
	void mapping() {
		Properties prop = new Properties();
		OutputStream os = null;
		prop.setProperty("host-name", "smtp.gmail.com");
		prop.setProperty("smtp-port", "465");
		prop.setProperty("username", "pfinal.aor@gmail.com");
		prop.setProperty("password", "emiliaricardo");
		prop.setProperty("ssl-on-connect", "true");
		try {
			os = new FileOutputStream("mail.properties");
			prop.store(os, null);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		IUser admin = builder.email("admin@email.com").firstName("Administrador").lastName("do Sistema")
				.password("123456").role(Role.ADMIN).username("ricardo").build();
		persistence.create(admin);
		IUser gestor = builder.email("ricardoquirino@me.com").firstName("Gestor").lastName("de Candidaturas")
				.password("123456").role(Role.GESTOR).username("quirino").build();
		persistence.create(gestor);
		// dozermapping.add("META-INF/dtomapping.xml");
	}

	public static DozerBeanMapper getInstance() {
		return MapperHolder.instance;

	}

	private static class MapperHolder {
		static final DozerBeanMapper instance = new DozerBeanMapper(DozerMapperSingl.dozermapping);
	}

}
