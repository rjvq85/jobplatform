package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.List;

import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IUser;

public interface IInterviewBusinessService {
	
	List<IInterview> getAllInterviews();
	
	List<IInterview> getInterviewsByInterviewer(Integer id);

	List<IInterview> getInterviewsByDate(LocalDate date);
	
	List<IInterview> getInterviewsByDatePeriod(LocalDate dateInit,LocalDate dateFinal);

	List<IInterview> getInterviewsByDateAndInterviewer(LocalDate date, Object currentUserID);

	IInterview newInterview(IInterview interview);

	IInterview updateInterview(IInterview selectedInterview);

	void deleteInterview(IInterview selectedInterview);

	List<IUser> getAvailableInterviewers(Integer id);

	List<IScript> getAvailableScripts(Integer id);

	List<IInterview> getInterviewsByCandidate(ICandidate candidate);

	IInterview getInterviewsById(Integer id);

	List<IInterview> getScheduledInterviews();

	Long countInterviewsPerScript(Integer scriptId);

	List<IInterview> getByScript(Integer id);

	void updateMultiple(List<IInterview> interviews);

	List<IInterview> getDoneByCandidate(Integer candidateId);


}
