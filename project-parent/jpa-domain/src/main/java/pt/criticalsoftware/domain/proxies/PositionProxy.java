package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;
import java.util.Collection;

import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;

public class PositionProxy implements IEntityAware<PositionEntity>, IPosition {
	
	private PositionEntity position;
	
	public PositionProxy() {
		this(null);
	}
	
	public PositionProxy(PositionEntity entity) {
		this.position = entity != null ? entity : new PositionEntity();
	}
	
	@Override
	public PositionEntity getEntity() {
		return this.position;
	}

	@Override
	public Integer getId() {
		return position.getId();
	}

	@Override
	public void setOpenDate(LocalDate openDate) {
		position.setOpenDate(openDate);
		
	}

	@Override
	public LocalDate getOpenDate() {
		return position.getOpenDate();
		
	}

	@Override
	public void setCloseDate(LocalDate closeDate) {
		position.setCloseDate(closeDate);
		
	}

	@Override
	public LocalDate getCloseDate() {
		return position.getCloseDate();
		
	}

	@Override
	public String getReference() {
		return position.getReference() ;
	}

	@Override
	public void setReference(String reference) {
		position.setReference(reference);
		
	}

	@Override
	public String getTitle() {
		return position.getTitle();
	}

	@Override
	public void setTitle(String title) {
		position.setTitle(title);
		
	}

	@Override
	public String getLocale() {
		return position.getLocale();
	}

	@Override
	public void setLocale(String locale) {
		position.setLocale(locale);
	}

	@Override
	public String getCompany() {
		return position.getCompany();
	}

	@Override
	public void setCompany(String company) {
		position.setCompany(company);
	}

	@Override
	public TechnicalAreaType getTechnicalArea() {
		return position.getTechnicalArea();
	}

	@Override
	public void setTechnicalArea(TechnicalAreaType technicalArea) {
		position.setTechnicalArea(technicalArea);
	}

	@Override
	public String getSla() {
		return position.getSla();
	}

	@Override
	public void setSla(String sla) {
		position.setSla(sla);
	}

	@Override
	public String getDescription() {
		return position.getDescription();
	}

	@Override
	public void setDescription(String description) {
		position.setDescription(description);
	}

	@Override
	public PositionState getState() {
		return position.getState();
	}

	@Override
	public void setState(PositionState state) {
		position.setState(state);
	}

	@Override
	public Integer getVacancies() {
		return position.getVacancies();
	}

	@Override
	public void setVacancies(Integer vacancies) {
		position.setVacancies(vacancies);
	}

	@Override
	public Collection<String> getAdChannels() {
		return position.getAdChannels();
	}

	@Override
	public void setAdChannels(Collection<String> adChannels) {
		position.setAdChannels(adChannels);
		
	}

	@Override
	public IUser getResponsable() {
		return (IUser) position.getResponsable();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setResponsable(IUser responsable) {
		if (responsable instanceof IEntityAware<?>) {
			position.setResponsable(((IEntityAware<UserEntity>)responsable).getEntity());
		}
	}
	
	@Override
	public String toString() {
		if (null == position.getId()) return "Espontânea";
		return position.getId().toString();
	}

}
