package pt.criticalsoftware.service.model;

import java.time.LocalDate;
import java.util.Collection;

import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;

public interface IPosition {

	Integer getId();

	void setOpenDate(LocalDate openDate);

	LocalDate getOpenDate();

	void setCloseDate(LocalDate closeDate);

	LocalDate getCloseDate();

	String getReference();

	void setReference(String reference);

	String getTitle();

	void setTitle(String title);

	String getLocale();

	void setLocale(String locale);

	String getCompany();

	void setCompany(String company);

	TechnicalAreaType getTechnicalArea();

	void setTechnicalArea(TechnicalAreaType technicalArea);

	String getSla();

	void setSla(String sla);

	String getDescription();

	void setDescription(String description);

	PositionState getState();

	void setState(PositionState state);

	Integer getVacancies();

	void setVacancies(Integer vacancies);

	Collection<String> getAdChannels();

	void setAdChannels(Collection<String>  adChannels);

	IUser getResponsable();

	void setResponsable(IUser responsable);

}
