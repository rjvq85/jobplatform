package pt.criticalsoftware.service.persistence;

import java.time.LocalDate;
import java.util.List;

import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IUser;

public interface IInterviewPersistenceService {
	
	List<IInterview> getAll();
	
	List<IInterview> getByInterviewer(Integer id);
	
	List<IInterview> getByReference();

	List<IInterview> getByDate(LocalDate date);
	
	List<IInterview> getInterviewsByDatePeriod(LocalDate dateInit,LocalDate dateFinal);	
	
	List<IInterview> getByDate(LocalDate date, Object id);
	
	List<IInterview> getByPosition();

	IInterview create(IInterview entity);

	IInterview update(IInterview selectedInterview);

	void delete(IInterview selectedInterview);

	List<IUser> getAvailableInterviewers(Integer id);

	List<IScript> getAvailableScripts(Integer id);

	List<IInterview> getByCandidate(ICandidate candidate);

	IInterview find(Integer id);

	List<IInterview> getScheduled();

	Long getNumberWithScript(Integer id);

	List<IInterview> findByScript(Integer id);

	void updateMultiple(List<IInterview> interviews);

	List<IInterview> getDone();

	List<IInterview> getDoneInterviewsByCandidate(Integer candidateId);
}
