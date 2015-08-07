package pt.criticalsoftware.domain.proxies;

import javax.enterprise.context.RequestScoped;

import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IModelFactory;
import pt.criticalsoftware.service.model.IUser;

@RequestScoped
public class ModelFactory implements IModelFactory {
	
	@Override
	public IUser user() {
		return new UserProxy();
	}
	
	@Override
	public ICandidate candidate() {
		return new CandidateProxy();
	}

}
