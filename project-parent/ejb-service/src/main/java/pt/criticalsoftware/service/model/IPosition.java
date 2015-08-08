package pt.criticalsoftware.service.model;

import java.time.LocalDate;

import pt.criticalsoftware.service.persistence.states.PositionState;

public interface IPosition {

	Integer getId();

	void setOpenDate(LocalDate openDate);

	LocalDate getOpenDate();

	void setCloseDate(LocalDate closeDate);

	LocalDate getCloseOpen();

	String getReference();

	void setReference(String reference);
	
	String getTitle();

	void setTitle(String title);
	
	String getLocale();

	void setLocale(String locale);
	
	String getCompany();

	void setCompany(String company);
	
	String getTechnicalArea();

	void setTechnicalArea(String technicalArea);
	
	String getSla();

	void setSla(String sla);
	
	String getDescription();

	void setDescription(String description);
	
	PositionState getState();

	void setState(PositionState state);
	
	Integer getVacancies();

	void setVacancies(Integer vacancies);
		
}
