package pt.criticalsoftware.domain.proxies;

import javax.enterprise.context.RequestScoped;

import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IInterviewBuilder;

@RequestScoped
public class InterviewBuilder implements IInterviewBuilder {
	
	private InterviewProxy interview;
	
	public InterviewBuilder() {
		interview = new InterviewProxy();
	}
	
	
	
	@Override
	public IInterview build() {
		return interview;
	}

}
