package pt.criticalsoftware.service.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;

public interface IPositionBuilder {

	IPositionBuilder openDate(LocalDate openDate);
	IPositionBuilder closeDate(Date closeDate);
	IPositionBuilder reference(String reference);
	IPositionBuilder title(String title);
	IPositionBuilder locale(String locale);
	IPositionBuilder state(PositionState state);
	IPositionBuilder company(String company);
	IPositionBuilder technicalArea(TechnicalAreaType technicalArea);
	IPositionBuilder sla(Integer sla);
	IPositionBuilder vacancies(Integer vacancies);
	IPositionBuilder description(String description);
	IPositionBuilder adChannels(Collection<String> adChannels);
	IPositionBuilder responsable(IUser responsable);	
	IPosition build();

}
