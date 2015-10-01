package pt.criticalsoftware.service.model.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.dozer.DozerBeanMapper;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.business.IStyleBusinessService;

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
	IStyleBusinessService businessStyle;
	
	@EJB
	private ICandidacyBusinessService business;
	
	@EJB
	private IPositionBusinessService posBness;

	@PostConstruct
	void mapping() {
//		IUser admin = builder.email("ricardojvquirino@gmail.com").firstName("Administrador").lastName("do Sistema")
//				.password("123456").role(Role.ADMIN).username("ricardo").build();
//		persistence.create(admin);
//		IUser gestor = builder.email("ricardoquirino@me.com").firstName("Gestor").lastName("de Candidaturas")
//				.password("123456").role(Role.GESTOR).username("quirino").build();
//		persistence.create(gestor);
		String tt="Fundada em 1998, A CRITICAL Software é especializada no desenvolvimento de soluções de "
				+ "software e serviços de engenharia de informação, para o suporte de sistemas críticos orientados à "
				+ "segurança, missão e ao negócio de empresas. Ajudamos os nossos clientes a assegurar que os seus processos críticos "
				+ "de negócio são realizados de acordo com os mais exigentes padrões de qualidade no que respeita à segurança do "
				+ "software, ao desempenho e à fiabilidade. "
				+ "Os nossos produtos e serviços também fornecem aos clientes a informação necessária para a gestão eficiente e "
				+ "segura dos seus ativos importantes, ajudando-os a alcançar um melhor desempenho nos negócios.";
		businessStyle.saveTheme(tt,"Default", "logocritical.png");
		 dozermapping.add("META-INF/dtomapping.xml");
	}

	public static DozerBeanMapper getInstance() {
		return MapperHolder.instance;

	}

	private static class MapperHolder {
		static final DozerBeanMapper instance = new DozerBeanMapper(DozerMapperSingl.dozermapping);
	}

}
