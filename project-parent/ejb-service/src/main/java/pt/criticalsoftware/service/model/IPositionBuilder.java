package pt.criticalsoftware.service.model;

import java.time.LocalDate;

import pt.criticalsoftware.service.persistence.states.PositionState;

public interface IPositionBuilder {
	

	
	IPositionBuilder id(Integer id);
	IPositionBuilder openDate(LocalDate openDate);
	IPositionBuilder closeDate(LocalDate closeDate);
	IPositionBuilder reference(String reference);
	IPositionBuilder title(String title);
	IPositionBuilder locale(String locale);
	IPositionBuilder state(PositionState state);
	IPositionBuilder company(String company);
	IPositionBuilder technicalArea(String technicalArea);
	IPositionBuilder sla(String sla);
	IPositionBuilder vacancies(Integer vacancies);
	IPositionBuilder description(String description);
	
	IPosition build();

}
