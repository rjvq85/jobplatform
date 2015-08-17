package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.persistence.states.PositionState;

public interface IPositionBusinessService {
	
	List<IPosition> getAllPositions();
	
	List<IPosition> getPositionsByWord(String positionWord,String searchCode);
	
	void createPosition(LocalDate openDate, LocalDate closeDate,String reference,String title,String locale,
	PositionState state,String company, TechnicalAreaType technicalArea,String sla, Integer vacancies,IUser responsable,
	String description, Collection<String> adChannels) throws DuplicateReferenceException;

	void update(IPosition position);
	
	void delete(IPosition position);
	
	void verifyReference(String reference) throws DuplicateReferenceException;
	

}
