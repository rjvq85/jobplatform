package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.persistence.states.PositionState;

public class PositionProxy implements IEntityAware<PositionEntity>, IPosition {
	
	private PositionEntity position;
	
	public PositionProxy() {
		this(null);
	}
	
	public PositionProxy(PositionEntity entity) {
		position = entity != null ? entity : new PositionEntity();
	}
	
	@Override
	public PositionEntity getEntity() {
		return this.position;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOpenDate(LocalDate openDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LocalDate getOpenDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCloseDate(LocalDate closeDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LocalDate getCloseOpen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReference(String reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocale(String locale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCompany(String company) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTechnicalArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTechnicalArea(String technicalArea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSla() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSla(String sla) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PositionState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(PositionState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVacancies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVacancies(Integer vacancies) {
		// TODO Auto-generated method stub
		
	}

}
