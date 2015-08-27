package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.IInterviewPersistenceService;

@Stateless
public class InterviewBusinessService implements IInterviewBusinessService {
	
	@EJB
	private IInterviewPersistenceService persistence;
	
	@Override
	public List<IInterview> getAllInterviews() {
		return persistence.getAll();
	}

	@Override
	public List<IInterview> getInterviewsByInterviewer(Integer id) {
		return persistence.getByInterviewer(id);
	}

	@Override
	public List<IInterview> getInterviewsByDate(LocalDate date) {
		return persistence.getByDate(date);
	}

	@Override
	public List<IInterview> getInterviewsByDateAndInterviewer(LocalDate date, Object currentUserID) {
		return persistence.getByDate(date,currentUserID);
	}

	@Override
	public IInterview newInterview(IInterview interview) {
		return persistence.create(interview);
	}

	@Override
	public IInterview updateInterview(IInterview selectedInterview) {
		return persistence.update(selectedInterview);
	}

	@Override
	public void deleteInterview(IInterview selectedInterview) {
		persistence.delete(selectedInterview);
	}

	@Override
	public List<IUser> getAvailableInterviewers(Integer id) {
		return persistence.getAvailableInterviewers(id);
	}

	@Override
	public List<IScript> getAvailableScripts(Integer id) {
		return persistence.getAvailableScripts(id);
	}
	

}
