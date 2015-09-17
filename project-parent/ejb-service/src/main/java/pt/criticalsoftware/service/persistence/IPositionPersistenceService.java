package pt.criticalsoftware.service.persistence;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.model.IPosition;

public interface IPositionPersistenceService {

	List<IPosition> getAllPositions();
	
	List<IPosition>  getPositionsByWord(String positionWord,String searchCode);
	
	List<IPosition>  getPositionsByKeyWords(String positionWord,String searchCode);
	
	void verifyReference(String reference) throws DuplicateReferenceException;
	
	IPosition create(IPosition position);
	
	IPosition update(IPosition position);
	
	IPosition delete(IPosition position);

	List<IPosition> getPositionsByDate(String positionWord, Date closedate);
	
	List<IPosition> getPositionsByOpenDate(String positionWord, LocalDate opendate);

	IPosition find(Object id);

	List<IPosition> getManagerPositions(Integer currentUserID);

	List<IPosition> getPositionsByLocaleAndArea(String locale, String technicalAreaStr);

	List<IPosition> getPositionsByLocale(String locale);

	List<IPosition> getPositionsByTechnicalArea(String technicalArea);

	List<IPosition> getPositionsByLast();

	List<IPosition> getAllOpenPositions();

}
