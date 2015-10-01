package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;

public interface IPositionBusinessService {

	List<IPosition> getAllPositions();

	List<IPosition> getPositionsByKeyWords(String positionWord, String searchCode);

	List<IPosition> getPositionsByWord(String positionWord, String searchCode);

	List<IPosition> getPositionsByDate(String positionWord, Date ld);

	List<IPosition> getPositionsByOpenDate(String positionWord, LocalDate openDate);

	List<IPosition> getPositionsByOpenDateByPeriod(LocalDate dateInit,LocalDate dateFinal);
	
	void update(IPosition position);

	void delete(IPosition position);

	void verifyReference(String reference) throws DuplicateReferenceException;

	List<IPosition> getManagerPositions(Integer currentUserID);

	IPosition createPosition(IPosition position) throws DuplicateReferenceException;

	IPosition getPositionById(Integer positionId);

	List<IPosition> getPositionsByLocaleAndArea(String locale, String technicalAreaStr);

	List<IPosition> getPositionsByLocale(String locale);

	List<IPosition> getPositionsByTechnicalArea(String technicalArea);

	List<IPosition> getPositionsByLast();

	IPosition createPosition(LocalDate openDate, Date closeDate, String title, String locale, PositionState state,
			String company, TechnicalAreaType technicalArea, Integer sla, Integer vacancies, IUser responsable,
			String description, Collection<String> adChannels) throws DuplicateReferenceException;
	
	List<IPosition> getOpenPositions();

	void setFirstHiring(IPosition position);

	List<IPosition> getNotAssignedPositions(Integer candidateId);

}
