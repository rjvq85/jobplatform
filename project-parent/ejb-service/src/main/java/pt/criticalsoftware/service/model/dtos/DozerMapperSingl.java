package pt.criticalsoftware.service.model.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * Session Bean implementation class DozerMapperSingl
 */
@Singleton
@Startup
public class DozerMapperSingl {
	
	private static List<String> dozermapping = new ArrayList<>();
	public static Mapper dozerMapper;
	
	@PostConstruct
	void mapping() {
		dozermapping.add("META-INF/dtomapping.xml");
		dozerMapper = DozerMapperSingl.getInstance();
	}
	
	public static DozerBeanMapper getInstance(){
	    return MapperHolder.instance;
	}

	private static class MapperHolder {
	    static final DozerBeanMapper instance = new DozerBeanMapper(DozerMapperSingl.dozermapping);
	}

}
