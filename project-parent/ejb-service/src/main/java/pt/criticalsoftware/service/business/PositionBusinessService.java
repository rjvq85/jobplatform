package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IPositionBuilder;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.IPositionPersistenceService;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;


@Stateless
public class PositionBusinessService implements IPositionBusinessService{
	

	private final Logger logger = LoggerFactory.getLogger(PositionBusinessService.class);
	@EJB
	private IPositionPersistenceService positionPersistence;

	@EJB
	private IPositionBuilder positionBuilder;

	@Override
	public List<IPosition> getAllPositions() {
		return positionPersistence.getAllPositions();
	}



	@Override
	public void verifyReference(String reference)
			throws DuplicateReferenceException {
		positionPersistence.verifyReference(reference);
	}



	@Override
	public IPosition createPosition(LocalDate openDate, LocalDate closeDate,
			String reference, String title, String locale, PositionState state,
			String company, TechnicalAreaType technicalArea, String sla,
			Integer vacancies, IUser responsable, String description,
			Collection<String> adChannels)
					throws DuplicateReferenceException {

		verifyReference(reference);
				
		IPosition position = positionBuilder.closeDate(closeDate)
				.company(company).description(description)
				.locale(locale).openDate(openDate)
				.reference(reference).sla(sla)
				.state(state).technicalArea(technicalArea)
				.title(title).vacancies(vacancies).adChannels(adChannels).responsable(responsable).build();
		
		return positionPersistence.create(position);

	}
	
	@Override
	public IPosition createPosition(IPosition position) throws DuplicateReferenceException {
		verifyReference(position.getReference());
		logger.debug("Posição criada");
		return positionPersistence.create(position);
	}
	
	@Override
	public void update(IPosition position) {
		positionPersistence.update(position);
		
	}
	
	@Override
	public void delete(IPosition position) {
		positionPersistence.delete(position);
		
	}

	@Override
	public List<IPosition> getPositionsByWord(String positionWord,
			String searchCode) {
		return positionPersistence.getPositionsByWord(positionWord, searchCode);
		
	}



	@Override
	public List<IPosition> getManagerPositions(Integer currentUserID) {
		return positionPersistence.getManagerPositions(currentUserID);
	}

}
