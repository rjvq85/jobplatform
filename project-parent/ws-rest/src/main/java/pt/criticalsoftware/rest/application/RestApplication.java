package pt.criticalsoftware.rest.application;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import pt.criticalsoftware.rest.resources.Candidate;

@ApplicationPath("/api")
public class RestApplication extends Application {
	
	
	@Override
	public Set<Class<?>> getClasses() {
	    Set<Class<?>> resources = new java.util.HashSet<>();
	    addRestResourceClasses(resources);
	    return resources;
	}
	

	private void addRestResourceClasses(Set<Class<?>> resources) {
		// resources.add(Users.class);
		resources.add(Candidate.class);
	}


}
