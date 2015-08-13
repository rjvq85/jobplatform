package pt.criticalsoftware.domain.proxies;
import javax.enterprise.context.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collection;

import pt.criticalsoftware.domain.service.UserPersistenceService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IPositionBuilder;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;

@RequestScoped
public class PositionBuilder implements IPositionBuilder {

	private final Logger logger = LoggerFactory.getLogger(UserPersistenceService.class);
	private PositionProxy position;
		
	public PositionBuilder() {
		this.position = new PositionProxy();
	}

	@Override
	public IPositionBuilder openDate(LocalDate openDate) {
		position.setOpenDate(openDate);
		return this;
	}

	@Override
	public IPositionBuilder closeDate(LocalDate closeDate) {
		position.setCloseDate(closeDate);
		return this;
	}

	@Override
	public IPositionBuilder reference(String reference) {
		position.setReference(reference);
		return this;
	}

	@Override
	public IPositionBuilder title(String title) {
		position.setTitle(title);
		return this;
	}

	@Override
	public IPositionBuilder locale(String locale) {
		position.setLocale(locale);
		return this;
	}

	@Override
	public IPositionBuilder state(PositionState state) {
		position.setState(state);
		return this;
	}

	@Override
	public IPositionBuilder company(String company) {
		position.setCompany(company);
		return this;
	}

	@Override
	public IPositionBuilder technicalArea(TechnicalAreaType technicalArea) {
		position.setTechnicalArea(technicalArea);
		return this;
	}

	@Override
	public IPositionBuilder sla(String sla) {
		position.setSla(sla);		
		return this;
	}

	@Override
	public IPositionBuilder vacancies(Integer vacancies) {
		position.setVacancies(vacancies);
		return this;
	}

	@Override
	public IPositionBuilder description(String description) {
		position.setDescription(description);	
		return this;
	}

	@Override
	public IPositionBuilder adChannels(Collection<String> adChannels) {
		position.setAdChannels(adChannels);
		return this;
	}

	@Override
	public IPositionBuilder responsable(IUser responsable) {
		position.setResponsable(responsable);
		return this;
	}
	
	@Override
	public IPosition build() {
		return position;
	}


}
