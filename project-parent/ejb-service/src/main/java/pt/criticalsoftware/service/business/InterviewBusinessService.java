package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.ICandidate;
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
		return persistence.getByDate(date, currentUserID);
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

	@Override
	public List<IInterview> getInterviewsByDatePeriod(LocalDate dateInit, LocalDate dateFinal) {
		return persistence.getInterviewsByDatePeriod(dateInit, dateFinal);
	}

	@Override
	public List<IInterview> getInterviewsByCandidate(ICandidate candidate) {
		return persistence.getByCandidate(candidate);
	}

	@Override
	public IInterview getInterviewsById(Integer id) {
		return persistence.find(id);
	}
	
	@Override
	public List<IInterview> getScheduledInterviews() {
		return persistence.getScheduled();
	}
	
	@Override
	public Long countInterviewsPerScript(Integer scriptId) {
		return persistence.getNumberWithScript(scriptId);
	}

	@Override
	public List<IInterview> getByScript(Integer id) {
		return persistence.findByScript(id);
	}

	@Override
	public void updateMultiple(List<IInterview> interviews) {
		persistence.updateMultiple(interviews);
	}
	
	@Override
	public List<IInterview> getDoneByCandidate(Integer candidateId) {
		return persistence.getDoneInterviewsByCandidate(candidateId);
	}

}
