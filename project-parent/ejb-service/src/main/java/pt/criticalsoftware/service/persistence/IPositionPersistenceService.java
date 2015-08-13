package pt.criticalsoftware.service.persistence;

import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;

public interface IPositionPersistenceService {

	List<IPosition> getAllPositions();
	
	void verifyReference(String reference) throws DuplicateReferenceException;
	
	IPosition create(IPosition position);
	
	IPosition update(IPosition position);
	
	IPosition delete(IPosition position);

}
